package com.ibrahimkvlci.ecommerce.catalog.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MaterializedViewInitializer {

    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeSearchInfrastructure() {
        try {
            createSearchVectorColumn();
            createMaterializedView();
        } catch (Exception e) {
            log.error("Failed to initialize search infrastructure: {}", e.getMessage(), e);
        }
    }

    private void createSearchVectorColumn() {
        // Add search_vector column if it doesn't exist
        Boolean columnExists = jdbcTemplate.queryForObject("""
                SELECT EXISTS (
                    SELECT 1 FROM information_schema.columns
                    WHERE table_name = 'products' AND column_name = 'search_vector'
                )
                """, Boolean.class);

        if (Boolean.TRUE.equals(columnExists)) {
            log.info("Column 'search_vector' already exists on 'products' table.");
            return;
        }

        log.info("Adding 'search_vector' column to 'products' table...");

        // Add the tsvector column
        jdbcTemplate.execute(
                "ALTER TABLE products ADD COLUMN search_vector tsvector");

        // Populate existing rows
        jdbcTemplate.execute("""
                UPDATE products SET search_vector =
                    setweight(to_tsvector('turkish', coalesce(title, '')), 'A') ||
                    setweight(to_tsvector('turkish', coalesce(description, '')), 'B')
                """);

        // Create GIN index for fast full-text search
        jdbcTemplate.execute(
                "CREATE INDEX idx_products_search_vector ON products USING gin(search_vector)");

        // Create trigger function to auto-update search_vector
        jdbcTemplate.execute("""
                CREATE OR REPLACE FUNCTION products_search_vector_update() RETURNS trigger AS $$
                BEGIN
                    NEW.search_vector :=
                        setweight(to_tsvector('turkish', coalesce(NEW.title, '')), 'A') ||
                        setweight(to_tsvector('turkish', coalesce(NEW.description, '')), 'B');
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql
                """);

        // Create trigger
        jdbcTemplate.execute("""
                CREATE TRIGGER trg_products_search_vector_update
                BEFORE INSERT OR UPDATE OF title, description ON products
                FOR EACH ROW EXECUTE FUNCTION products_search_vector_update()
                """);

        log.info("Search vector column, index, and trigger created successfully.");
    }

    private void createMaterializedView() {
        Boolean exists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM pg_matviews WHERE matviewname = 'unique_keywords')",
                Boolean.class);

        if (Boolean.TRUE.equals(exists)) {
            log.info("Materialized view 'unique_keywords' already exists, refreshing...");
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY unique_keywords");
        } else {
            log.info("Creating materialized view 'unique_keywords'...");
            jdbcTemplate.execute("""
                    CREATE MATERIALIZED VIEW unique_keywords AS
                    SELECT word, nentry
                    FROM ts_stat('SELECT search_vector FROM public.products')
                    WHERE word IS NOT NULL AND word != ''
                    """);
            jdbcTemplate.execute(
                    "CREATE UNIQUE INDEX idx_unique_keywords ON unique_keywords(word)");
            log.info("Materialized view 'unique_keywords' created successfully.");
        }
    }
}

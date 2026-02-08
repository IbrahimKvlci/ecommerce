package com.ibrahimkvlci.ecommerce.catalog.repositories;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.repositories.projection.AttributeSummary;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find products by title containing the given keyword (case-insensitive)
     */
    List<Product> findByTitleContainingIgnoreCase(String title);

    /**
     * Find products by description containing keyword
     */
    List<Product> findByDescriptionContaining(String keyword);

    /**
     * Check if product exists by title
     */
    boolean existsByTitle(String title);

    /**
     * Find products by category
     */
    List<Product> findByCategory(Category category);

    /**
     * Find products by category ID
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Find products by brand
     */
    List<Product> findByBrand(Brand brand);

    /**
     * Find products by brand ID
     */
    List<Product> findByBrandId(Long brandId);

    List<Product> findByCategoryIdAndFeaturedTrue(Long categoryId);

    List<Product> findByCategoryIdAndFeaturedTrueAndInventoriesIsNotEmpty(Long categoryId);

    @Query(value = """
            SELECT p.*, ts_rank(search_vector, plainto_tsquery('turkish', :searchTerm)) as rank
            FROM products p
            WHERE search_vector @@ plainto_tsquery('turkish', :searchTerm) AND EXISTS (
                SELECT 1
                FROM inventories i
                WHERE i.product_id = p.id
            )
            ORDER BY rank DESC
            """, nativeQuery = true)
    List<Product> searchWithRankingAndInventoriesNotEmpty(@Param("searchTerm") String searchTerm);

    @Query(value = """
            SELECT word
            FROM unique_keywords
            WHERE word ILIKE CONCAT(:prefix, '%')
            ORDER BY nentry DESC
            LIMIT 10
            """, nativeQuery = true)
    List<String> findKeywordSuggestions(@Param("prefix") String prefix);

    @Query(value = """
            SELECT
                key   AS key,
                value AS value,
                COUNT(*) AS count
            FROM products,
                 jsonb_each_text(attributes)
            WHERE category_id = :categoryId
            GROUP BY key, value
            ORDER BY key, count DESC
            """, nativeQuery = true)
    List<AttributeSummary> findAttributeStats(@Param("categoryId") Long categoryId);

    @Query(value = """
            SELECT
                key   AS key,
                value AS value,
                COUNT(*) AS count
            FROM products,
                 jsonb_each_text(attributes)
            WHERE category_id IN :categoryIds
            GROUP BY key, value
            ORDER BY key, count DESC
            """, nativeQuery = true)
    List<AttributeSummary> findAttributeStats(@Param("categoryIds") List<Long> categoryIds);

}

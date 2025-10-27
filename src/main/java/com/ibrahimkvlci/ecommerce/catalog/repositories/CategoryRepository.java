package com.ibrahimkvlci.ecommerce.catalog.repositories;

import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Find category by name (case-insensitive)
     */
    Optional<Category> findByNameIgnoreCase(String name);
    
    /**
     * Find categories by name containing the given keyword (case-insensitive)
     */
    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByParentIsNull();

    List<Category> findByParentId(Long id);
    
    /**
     * Check if category exists by name
     */
    boolean existsByName(String name);
    
    /**
     * Check if category exists by name (case-insensitive)
     */
    boolean existsByNameIgnoreCase(String name);
}

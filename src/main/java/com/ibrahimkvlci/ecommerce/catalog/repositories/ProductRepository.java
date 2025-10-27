package com.ibrahimkvlci.ecommerce.catalog.repositories;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("""
    SELECT new com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO(
        p.id,
        p.title,
        p.description,
        p.brand.name,
        i.price,
        i.sellerId
    )
    FROM Product p
    JOIN Inventory i ON i.product.id = p.id
    WHERE p.category.id = :categoryId
    AND p.featured = true
    AND i.price <= ALL(
        SELECT i2.price
        FROM Inventory i2
        WHERE i2.product.id = p.id
    )
    """)
    List<ProductDisplayDTO> findByCategoryIdAndFeaturedTrueWithLowestPriceInventory(Long categoryId);
}

package com.ibrahimkvlci.ecommerce.catalog.repositories;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Find products by title containing the given keyword (case-insensitive)
     */
    List<Product> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find products by price range
     */
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    
    /**
     * Find products with price less than or equal to the given price
     */
    List<Product> findByPriceLessThanEqual(double price);
    
    /**
     * Find products with price greater than or equal to the given price
     */
    List<Product> findByPriceGreaterThanEqual(double price);
    
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

}

package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    /**
     * Create a new product
     */
    Product createProduct(Product product);
    
    /**
     * Get all products
     */
    List<Product> getAllProducts();
    
    /**
     * Get product by ID
     */
    Optional<Product> getProductById(Long id);
    
    /**
     * Update an existing product
     */
    Product updateProduct(Long id, Product product);
    
    /**
     * Delete a product by ID
     */
    void deleteProduct(Long id);
    
    /**
     * Search products by title
     */
    List<Product> searchProductsByTitle(String title);
    
    /**
     * Get products by price range
     */
    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);
    
    /**
     * Get products with price less than or equal to given price
     */
    List<Product> getProductsByMaxPrice(double maxPrice);
    
    /**
     * Get products with price greater than or equal to given price
     */
    List<Product> getProductsByMinPrice(double minPrice);
    
    /**
     * Search products by description
     */
    List<Product> searchProductsByDescription(String keyword);
    
    /**
     * Check if product exists by title
     */
    boolean productExistsByTitle(String title);
    
    /**
     * Get products by category
     */
    List<Product> getProductsByCategory(Category category);
    
    /**
     * Get products by category ID
     */
    List<Product> getProductsByCategoryId(Long categoryId);
}

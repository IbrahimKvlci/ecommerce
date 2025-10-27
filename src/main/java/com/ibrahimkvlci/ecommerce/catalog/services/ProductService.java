package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;

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
    List<ProductDTO> getAllProducts();
    
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
     * Search products by description
     */
    List<Product> searchProductsByDescription(String keyword);
    
    /**
     * Check if product exists by title
     */
    boolean productExistsByTitle(String title);

    /**
     * Check if product is available
     */
    boolean isProductAvailable(Long productId);
    
    /**
     * Get products by category
     */
    List<Product> getProductsByCategory(Category category);
    
    /**
     * Get products by category ID
     */
    List<Product> getProductsByCategoryId(Long categoryId);

    /**
     * Get products by brand
     */
    List<Product> getProductsByBrand(Brand brand);
    
    /**
     * Get products by brand ID
     */
    List<Product> getProductsByBrandId(Long brandId);

    List<ProductDTO> getProductsByCategoryIdAndFeaturedTrue(Long categoryId);

    List<ProductDisplayDTO> getDisplayProductsByCategoryId(Long categoryId);
    
    /**
     * Convert DTO to entity
     */
    Product mapToEntity(ProductDTO productDTO);
    
    /**
     * Create DTO from entity
     */
    ProductDTO mapToDTO(Product product);
}

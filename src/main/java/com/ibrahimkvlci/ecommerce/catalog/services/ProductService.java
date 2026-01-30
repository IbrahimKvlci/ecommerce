package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductAddDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    /**
     * Create a new product
     */
    /**
     * Create a new product
     */
    DataResult<ProductDTO> createProduct(ProductAddDTO product, List<MultipartFile> images);

    /**
     * Get all products
     */
    DataResult<List<ProductDTO>> getAllProducts();

    /**
     * Get product by ID
     */
    DataResult<Product> getProductById(Long id);

    /**
     * Update an existing product
     */
    DataResult<Product> updateProduct(Long id, Product product);

    /**
     * Delete a product by ID
     */
    Result deleteProduct(Long id);

    /**
     * Search products by title
     */
    DataResult<List<Product>> searchProductsByTitle(String title);

    /**
     * Search products by description
     */
    DataResult<List<Product>> searchProductsByDescription(String keyword);

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
    DataResult<List<Product>> getProductsByCategory(Category category);

    /**
     * Get products by category ID
     */
    DataResult<List<Product>> getProductsByCategoryId(Long categoryId);

    /**
     * Get products by brand
     */
    DataResult<List<Product>> getProductsByBrand(Brand brand);

    /**
     * Get products by brand ID
     */
    DataResult<List<Product>> getProductsByBrandId(Long brandId);

    DataResult<List<ProductDTO>> getProductsByCategoryIdAndFeaturedTrue(Long categoryId);

    DataResult<List<ProductDisplayDTO>> getDisplayProductsByCategoryId(Long categoryId);

    /**
     * Convert DTO to entity
     */
    Product mapToEntity(ProductDTO productDTO);

    /**
     * Create DTO from entity
     */
    ProductDTO mapToDTO(Product product);
}

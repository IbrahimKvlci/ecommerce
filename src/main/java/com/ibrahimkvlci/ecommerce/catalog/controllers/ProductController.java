package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.services.ProductService;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Product operations.
 * Provides endpoints for CRUD operations and product search.
 */
@RestController
@RequestMapping("/api/catalog/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product
     */
    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<DataResult<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        DataResult<Product> productResult = productService.createProduct(productService.mapToEntity(productDTO));
        if (!productResult.isSuccess()) {
            return ResponseEntity.badRequest()
                    .body(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult<>(
                            productResult.getMessage(), null));
        }
        ProductDTO createdDTO = productService.mapToDTO(productResult.getData());
        return new ResponseEntity<>(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                "Product created successfully", createdDTO), HttpStatus.CREATED);
    }

    /**
     * Get all products
     */
    @GetMapping
    public ResponseEntity<DataResult<List<ProductDTO>>> getAllProducts(
            @CookieValue(value = "jwt", required = false) String jwtToken) {
        System.out.println("JWT Token: " + jwtToken);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResult<ProductDTO>> getProductById(@PathVariable Long id) {
        DataResult<Product> productResult = productService.getProductById(id);
        if (productResult.isSuccess()) {
            return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                    "Product found successfully", productService.mapToDTO(productResult.getData())));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    public ResponseEntity<DataResult<ProductDTO>> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        DataResult<Product> updatedProductResult = productService.updateProduct(id,
                productService.mapToEntity(productDTO));
        if (updatedProductResult.isSuccess()) {
            ProductDTO updatedDTO = productService.mapToDTO(updatedProductResult.getData());
            return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                    "Product updated successfully", updatedDTO));
        }
        return ResponseEntity.badRequest()
                .body(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult<>(
                        updatedProductResult.getMessage(), null));
    }

    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    /**
     * Search products by title
     */
    @GetMapping("/search/title")
    public ResponseEntity<DataResult<List<ProductDTO>>> searchProductsByTitle(@RequestParam String title) {
        DataResult<List<Product>> productsResult = productService.searchProductsByTitle(title);
        List<ProductDTO> productDTOs = productsResult.getData().stream()
                .map(productService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                "Products found successfully", productDTOs));
    }

    /**
     * Search products by description
     */
    @GetMapping("/search/description")
    public ResponseEntity<DataResult<List<ProductDTO>>> searchProductsByDescription(@RequestParam String keyword) {
        DataResult<List<Product>> productsResult = productService.searchProductsByDescription(keyword);
        List<ProductDTO> productDTOs = productsResult.getData().stream()
                .map(productService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                "Products found successfully", productDTOs));
    }

    /**
     * Get products by category ID
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<DataResult<List<ProductDTO>>> getProductsByCategoryId(@PathVariable Long categoryId) {
        DataResult<List<Product>> productsResult = productService.getProductsByCategoryId(categoryId);
        List<ProductDTO> productDTOs = productsResult.getData().stream()
                .map(productService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                "Products found successfully", productDTOs));
    }

    /**
     * Get products by brand
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<DataResult<List<ProductDTO>>> getProductsByBrandId(@PathVariable Long brandId) {
        DataResult<List<Product>> productsResult = productService.getProductsByBrandId(brandId);
        List<ProductDTO> productDTOs = productsResult.getData().stream()
                .map(productService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                "Products found successfully", productDTOs));
    }

    /**
     * Get featured products by category ID
     */
    @GetMapping("/category/{categoryId}/featured")
    public ResponseEntity<DataResult<List<ProductDTO>>> getFeaturedProductsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryIdAndFeaturedTrue(categoryId));
    }

    /**
     * Get featured products by category ID with lowest price inventory
     */
    @GetMapping("/category/{categoryId}/displayProducts")
    public ResponseEntity<DataResult<List<ProductDisplayDTO>>> getDisplayProductsByCategoryId(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getDisplayProductsByCategoryId(categoryId));
    }
}

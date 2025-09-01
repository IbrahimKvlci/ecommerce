package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.services.ProductService;
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
@CrossOrigin(origins = "*")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO.toEntity());
        ProductDTO createdDTO = ProductDTO.fromEntity(product);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }
    
    /**
     * Get all products
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(ProductDTO.fromEntity(product)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, 
                                                 @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO.toEntity());
        ProductDTO updatedDTO = ProductDTO.fromEntity(updatedProduct);
        return ResponseEntity.ok(updatedDTO);
    }
    
    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search products by title
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<ProductDTO>> searchProductsByTitle(@RequestParam String title) {
        List<Product> products = productService.searchProductsByTitle(title);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Search products by description
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<ProductDTO>> searchProductsByDescription(@RequestParam String keyword) {
        List<Product> products = productService.searchProductsByDescription(keyword);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Get products by price range
     */
    @GetMapping("/search/price-range")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @RequestParam double minPrice, 
            @RequestParam double maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Get products by maximum price
     */
    @GetMapping("/search/max-price")
    public ResponseEntity<List<ProductDTO>> getProductsByMaxPrice(@RequestParam double maxPrice) {
        List<Product> products = productService.getProductsByMaxPrice(maxPrice);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Get products by minimum price
     */
    @GetMapping("/search/min-price")
    public ResponseEntity<List<ProductDTO>> getProductsByMinPrice(@RequestParam double minPrice) {
        List<Product> products = productService.getProductsByMinPrice(minPrice);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    /**
     * Get products by category ID
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
}

package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.services.ProductService;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<DataResult<ProductDTO>> createProduct(
                        @Parameter(description = "Ürün bilgilerini içeren JSON objesi", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @RequestPart @Validated(ProductRequestDTO.OnCreate.class) ProductRequestDTO productDTO,
                        @RequestPart("images") List<MultipartFile> images) {
                DataResult<ProductDTO> productResult = productService.createProduct(productDTO, images);

                return ResponseEntity.ok(productResult);
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
                        return ResponseEntity.ok(
                                        new com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult<>(
                                                        "Product found successfully",
                                                        productService.mapToDTO(productResult.getData())));
                }
                return ResponseEntity.notFound().build();
        }

        /**
         * Update an existing product
         */
        @PutMapping("/{id}")
        public ResponseEntity<DataResult<ProductDTO>> updateProduct(@PathVariable Long id,
                        @RequestBody @Validated(ProductRequestDTO.OnUpdate.class) ProductRequestDTO productDTO) {
                DataResult<ProductDTO> updatedProductResult = productService.updateProduct(productDTO);
                return ResponseEntity.ok(updatedProductResult);
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
        public ResponseEntity<DataResult<List<ProductDTO>>> getFeaturedProductsByCategoryId(
                        @PathVariable Long categoryId) {
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

        @GetMapping("/search/ranking")
        public ResponseEntity<DataResult<List<ProductDisplayDTO>>> searchProductsWithRanking(
                        @RequestParam String searchTerm) {
                return ResponseEntity.ok(productService.searchProductsWithRanking(searchTerm));
        }

        @GetMapping("/search/suggestions")
        public ResponseEntity<DataResult<List<String>>> findKeywordSuggestions(@RequestParam String prefix) {
                System.out.println("Prefix: " + prefix);
                return ResponseEntity.ok(productService.findKeywordSuggestions(prefix));
        }
}

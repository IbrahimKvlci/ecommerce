package com.ibrahimkvlci.ecommerce.catalog.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setCategoryId(1L);
        productDTO.setBrandId(1L);

        when(productService.createProduct(productDTO.toEntity())).thenReturn(productDTO.toEntity());

        ResponseEntity<ProductDTO> response = productController.createProduct(productDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO.toEntity().getTitle(), response.getBody().getTitle());
        assertEquals(productDTO.toEntity().getDescription(), response.getBody().getDescription());
        assertEquals(productDTO.toEntity().getPrice(), response.getBody().getPrice());
        assertEquals(productDTO.toEntity().getCategory().getId(), response.getBody().getCategoryId());
        assertEquals(productDTO.toEntity().getBrand().getId(), response.getBody().getBrandId());
        
    }

    @Test
    public void testGetAllProducts() {
        Product p1 = new Product(1L, "P1", "D1", 10.0,
                new Category(1L, "C1", new ArrayList<Product>()),
                new Brand(1L, "B1", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        Product p2 = new Product(2L, "P2", "D2", 20.0,
                new Category(2L, "C2", new ArrayList<Product>()),
                new Brand(2L, "B2", new ArrayList<Product>()),
                new ArrayList<Inventory>());

        when(productService.getAllProducts()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("P1", response.getBody().get(0).getTitle());
        assertEquals("P2", response.getBody().get(1).getTitle());
    }

    @Test
    public void testGetProductByIdFound() {
        Product p = new Product(1L, "P1", "D1", 10.0,
                new Category(1L, "C1", new ArrayList<Product>()),
                new Brand(1L, "B1", new ArrayList<Product>()),
                new ArrayList<Inventory>());

        when(productService.getProductById(1L)).thenReturn(Optional.of(p));

        ResponseEntity<ProductDTO> response = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("P1", response.getBody().getTitle());
        assertEquals(1L, response.getBody().getCategoryId());
        assertEquals(1L, response.getBody().getBrandId());
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productService.getProductById(99L)).thenReturn(Optional.empty());

        ResponseEntity<ProductDTO> response = productController.getProductById(99L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() {
        ProductDTO updateDTO = new ProductDTO(1L, "Updated", "Desc", 30.0, 3L, 4L);
        Product updated = updateDTO.toEntity();

        when(productService.updateProduct(1L, updateDTO.toEntity())).thenReturn(updated);

        ResponseEntity<ProductDTO> response = productController.updateProduct(1L, updateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated", response.getBody().getTitle());
        assertEquals(3L, response.getBody().getCategoryId());
        assertEquals(4L, response.getBody().getBrandId());
    }

    @Test
    public void testDeleteProduct() {
        ResponseEntity<Void> response = productController.deleteProduct(5L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testSearchProductsByTitle() {
        Product p = new Product(1L, "Phone", "Great", 100.0,
                new Category(1L, "C1", new ArrayList<Product>()),
                new Brand(1L, "B1", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.searchProductsByTitle("Pho")).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.searchProductsByTitle("Pho");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Phone", response.getBody().get(0).getTitle());
    }

    @Test
    public void testSearchProductsByDescription() {
        Product p = new Product(2L, "Laptop", "Gaming", 1500.0,
                new Category(2L, "C2", new ArrayList<Product>()),
                new Brand(2L, "B2", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.searchProductsByDescription("Game")).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.searchProductsByDescription("Game");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getTitle());
    }

    @Test
    public void testGetProductsByPriceRange() {
        Product p = new Product(3L, "Mouse", "Wireless", 25.0,
                new Category(3L, "C3", new ArrayList<Product>()),
                new Brand(3L, "B3", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.getProductsByPriceRange(20.0, 30.0)).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.getProductsByPriceRange(20.0, 30.0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Mouse", response.getBody().get(0).getTitle());
    }

    @Test
    public void testGetProductsByMaxPrice() {
        Product p = new Product(4L, "Cable", "USB-C", 9.0,
                new Category(4L, "C4", new ArrayList<Product>()),
                new Brand(4L, "B4", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.getProductsByMaxPrice(10.0)).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.getProductsByMaxPrice(10.0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Cable", response.getBody().get(0).getTitle());
    }

    @Test
    public void testGetProductsByMinPrice() {
        Product p = new Product(5L, "Monitor", "4K", 300.0,
                new Category(5L, "C5", new ArrayList<Product>()),
                new Brand(5L, "B5", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.getProductsByMinPrice(299.0)).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.getProductsByMinPrice(299.0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Monitor", response.getBody().get(0).getTitle());
    }

    @Test
    public void testGetProductsByCategoryId() {
        Product p = new Product(6L, "Desk", "Office", 120.0,
                new Category(10L, "Furniture", new ArrayList<Product>()),
                new Brand(6L, "B6", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.getProductsByCategoryId(10L)).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.getProductsByCategoryId(10L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(10L, response.getBody().get(0).getCategoryId());
    }

    @Test
    public void testGetProductsByBrandId() {
        Product p = new Product(7L, "Chair", "Ergonomic", 200.0,
                new Category(7L, "C7", new ArrayList<Product>()),
                new Brand(20L, "Seating", new ArrayList<Product>()),
                new ArrayList<Inventory>());
        when(productService.getProductsByBrandId(20L)).thenReturn(Arrays.asList(p));

        ResponseEntity<List<ProductDTO>> response = productController.getProductsByBrandId(20L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(20L, response.getBody().get(0).getBrandId());
    }
}

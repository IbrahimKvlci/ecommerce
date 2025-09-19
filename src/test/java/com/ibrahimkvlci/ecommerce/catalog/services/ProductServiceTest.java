package com.ibrahimkvlci.ecommerce.catalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testCreateProduct() {
        Brand mockBrand = new Brand(1L, "Mock Brand",new ArrayList<Product>());
        Category mockCategory = new Category(1L, "Mock Category",new ArrayList<Product>());
        Product mockProduct = new Product(1L, "Mock Product", "TEST", mockCategory, mockBrand,new ArrayList<Inventory>());

        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        when(brandRepository.findById(mockProduct.getBrand().getId())).thenReturn(Optional.of(mockBrand));
        when(categoryRepository.findById(mockProduct.getCategory().getId())).thenReturn(Optional.of(mockCategory));


        Product createdProduct = productService.createProduct(mockProduct);

        assertEquals(mockProduct.getTitle(), createdProduct.getTitle());
    }

    @Test
    public void testCreateProductWithInvalidBrand() {
        Category mockCategory = new Category(1L, "Mock Category", new ArrayList<Product>());
        Brand invalidBrand = new Brand(2L, "Invalid Brand", new ArrayList<Product>());
        Product mockProduct = new Product(1L, "Mock Product", "TEST", mockCategory, invalidBrand, new ArrayList<Inventory>());

        when(brandRepository.findById(invalidBrand.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(mockProduct));
    }

    @Test
    public void testCreateProductWithInvalidCategory() {
        Brand mockBrand = new Brand(1L, "Mock Brand",new ArrayList<Product>());
        Category invalidCategory = new Category(2L, "Invalid Category", new ArrayList<Product>());
        Product mockProduct = new Product(1L, "Mock Product", "TEST", invalidCategory, mockBrand, new ArrayList<Inventory>());

        when(brandRepository.findById(mockBrand.getId())).thenReturn(Optional.of(mockBrand));
        when(categoryRepository.findById(invalidCategory.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(mockProduct));
    }
    
    @Test
    public void testGetProductByIdWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(0L));
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(-1L));
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(null));
    }
    
    @Test
    public void testSearchProductsByTitleWithBlankKeyword() {
        assertThrows(IllegalArgumentException.class, () -> productService.searchProductsByTitle("   "));
    }


    
    @Test
    public void testGetProductsByCategoryNull() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByCategory(null));
    }
    
    @Test
    public void testGetProductsByCategoryIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByCategoryId(0L));
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByCategoryId(-5L));
    }
    
    @Test
    public void testGetProductsByBrandNull() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByBrand(null));
    }
    
    @Test
    public void testGetProductsByBrandIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByBrandId(0L));
        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByBrandId(-10L));
    }
    
    @Test
    public void testDeleteProductWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(0L));
    }
    
    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(99L));
    }

    @Test
    public void testCreateProductWithDuplicateTitle() {
        Brand mockBrand = new Brand(1L, "Mock Brand", new ArrayList<Product>());
        Category mockCategory = new Category(1L, "Mock Category", new ArrayList<Product>());
        Product mockProduct = new Product(1L, "Duplicate Title", "TEST", mockCategory, mockBrand, new ArrayList<Inventory>());

        when(productRepository.existsByTitle("Duplicate Title")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(mockProduct));
    }
    
    @Test
    public void testCreateProductWithEmptyTitle() {
        Brand mockBrand = new Brand(1L, "Mock Brand", new ArrayList<Product>());
        Category mockCategory = new Category(1L, "Mock Category", new ArrayList<Product>());
        Product mockProduct = new Product(1L, "", "TEST", mockCategory, mockBrand, new ArrayList<Inventory>());

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(mockProduct));
    }

    @Test
    public void testCreateProductWithNegativePrice() {
        Brand mockBrand = new Brand(1L, "Mock Brand", new ArrayList<Product>());
        Category mockCategory = new Category(1L, "Mock Category", new ArrayList<Product>());
        Product mockProduct = new Product(1L, "Mock Product", "TEST", mockCategory, mockBrand, new ArrayList<Inventory>());

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(mockProduct));
    }
}

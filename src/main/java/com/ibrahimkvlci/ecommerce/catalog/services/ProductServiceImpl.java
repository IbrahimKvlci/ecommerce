package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    
    
    @Override
    public Product createProduct(Product product) {
        // Validate product data
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be null or empty");
        }
        
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        // Check if product with same title already exists
        if (productExistsByTitle(product.getTitle())) {
            throw new IllegalArgumentException("Product with title '" + product.getTitle() + "' already exists");
        }

        if(brandRepository.findById(product.getBrand().getId()).isEmpty()) {
            throw new IllegalArgumentException("Brand with ID " + product.getBrand().getId() + " not found");
        }
        if(categoryRepository.findById(product.getCategory().getId()).isEmpty()) {
            throw new IllegalArgumentException("Category with ID " + product.getCategory().getId() + " not found");
        }

        return productRepository.save(product);            

    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        return productRepository.findById(id);
    }
    
    @Override
    public Product updateProduct(Long id, Product product) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        // Check if product exists
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        
        // Validate product data
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be null or empty");
        }
        
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        // Check if another product with same title exists (excluding current product)
        Product existing = existingProduct.get();
        if (!existing.getTitle().equals(product.getTitle()) && productExistsByTitle(product.getTitle())) {
            throw new IllegalArgumentException("Product with title '" + product.getTitle() + "' already exists");
        }
        
        // Update fields
        existing.setTitle(product.getTitle());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        
        return productRepository.save(existing);
    }
    
    @Override
    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        
        productRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Search title cannot be null or empty");
        }
        return productRepository.findByTitleContainingIgnoreCase(title.trim());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }
        
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
        
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByMaxPrice(double maxPrice) {
        if (maxPrice < 0) {
            throw new IllegalArgumentException("Maximum price cannot be negative");
        }
        return productRepository.findByPriceLessThanEqual(maxPrice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByMinPrice(double minPrice) {
        if (minPrice < 0) {
            throw new IllegalArgumentException("Minimum price cannot be negative");
        }
        return productRepository.findByPriceGreaterThanEqual(minPrice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByDescription(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
        }
        return productRepository.findByDescriptionContaining(keyword.trim());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean productExistsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        return productRepository.existsByTitle(title.trim());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return productRepository.findByCategory(category);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategoryId(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByBrand(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }
        return productRepository.findByBrand(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByBrandId(Long brandId) {
        if (brandId == null || brandId <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
        return productRepository.findByBrandId(brandId);
    }
}

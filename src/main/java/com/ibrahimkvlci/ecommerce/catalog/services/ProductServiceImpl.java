package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.projection.AttributeSummary;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.mappers.ProductMapper;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessResult;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageService productImageService;

    @Override
    public DataResult<ProductDTO> createProduct(ProductRequestDTO product, List<MultipartFile> images) {
        // Validate product data
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be null or empty");
        }
        // Check if product with same title already exists
        if (productExistsByTitle(product.getTitle())) {
            throw new IllegalArgumentException("Product with title '" + product.getTitle() + "' already exists");
        }

        if (brandRepository.findById(Objects.requireNonNull(product.getBrandId())).isEmpty()) {
            throw new IllegalArgumentException("Brand with ID " + product.getBrandId() + " not found");
        }
        if (categoryRepository.findById(Objects.requireNonNull(product.getCategoryId())).isEmpty()) {
            throw new IllegalArgumentException("Category with ID " + product.getCategoryId() + " not found");
        }

        Product savedProduct = productRepository.save(productMapper.toEntity(product));
        for (MultipartFile image : images) {
            productImageService.uploadProductImage(image, savedProduct);
        }

        return new SuccessDataResult<>("Product created successfully", productMapper.toDTO(savedProduct));

    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<ProductDTO>> getAllProducts() {
        return new SuccessDataResult<>("Products listed successfully",
                productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<Product> getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult<>("Product not found",
                    null);
        }
        return new SuccessDataResult<>("Product found successfully", product);
    }

    @Override
    public DataResult<ProductDTO> updateProduct(ProductRequestDTO product) {

        // Validate product data
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be null or empty");
        }
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " not found");
        }

        convertProductRequestToProduct(product, existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);

        return new SuccessDataResult<>("Product updated successfully", productMapper.toDTO(updatedProduct));
    }

    private void convertProductRequestToProduct(ProductRequestDTO productRequestDTO, Product product) {
        Product productEntity = productMapper.toEntity(productRequestDTO);

        product.setDescription(productEntity.getDescription());
        product.setBrand(productEntity.getBrand());
        product.setCategory(productEntity.getCategory());
        product.setTitle(productEntity.getTitle());
        product.setFeatured(productEntity.isFeatured());
    }

    @Override
    public Result deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        productRepository.deleteById(id);
        return new SuccessResult("Product deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<Product>> searchProductsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Search title cannot be null or empty");
        }
        return new SuccessDataResult<>("Products found successfully",
                productRepository.findByTitleContainingIgnoreCase(title.trim()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<Product>> searchProductsByDescription(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
        }
        return new SuccessDataResult<>("Products found successfully",
                productRepository.findByDescriptionContaining(keyword.trim()));
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
    public DataResult<List<Product>> getProductsByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return new SuccessDataResult<>("Products found successfully", productRepository.findByCategory(category));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<Product>> getProductsByCategoryId(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
        return new SuccessDataResult<>("Products found successfully", productRepository.findByCategoryId(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<Product>> getProductsByBrand(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }
        return new SuccessDataResult<>("Products found successfully", productRepository.findByBrand(brand));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<Product>> getProductsByBrandId(Long brandId) {
        if (brandId == null || brandId <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
        return new SuccessDataResult<>("Products found successfully", productRepository.findByBrandId(brandId));
    }

    @Override
    public boolean isProductAvailable(Long productId) {
        return productRepository.existsById(Objects.requireNonNull(productId));
    }

    /**
     * Convert DTO to entity
     */
    public Product mapToEntity(ProductDTO productDTO) {
        return productMapper.toEntity(productDTO);
    }

    /**
     * Create DTO from entity
     */
    public ProductDTO mapToDTO(Product product) {
        return productMapper.toDTO(product);
    }

    @Override
    public DataResult<List<ProductDTO>> getProductsByCategoryIdAndFeaturedTrue(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
        List<Product> featuredProducts = productRepository.findByCategoryIdAndFeaturedTrue(categoryId);
        return new SuccessDataResult<>("Featured products found successfully", featuredProducts.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public DataResult<List<ProductDisplayDTO>> getDisplayProductsByCategoryId(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
        List<Product> featuredProducts = productRepository
                .findByCategoryIdAndFeaturedTrueAndInventoriesIsNotEmpty(categoryId);
        List<ProductDisplayDTO> productDisplayDTOList = featuredProducts.stream()
                .map(productMapper::toProductDisplayDTO)
                .collect(Collectors.toList());
        return new SuccessDataResult<>("Display products found successfully", productDisplayDTOList);
    }

    @Override
    public DataResult<List<ProductDisplayDTO>> searchProductsWithRankingAndInventoriesNotEmpty(String searchTerm) {
        List<Product> products = productRepository.searchWithRankingAndInventoriesNotEmpty(searchTerm);
        List<ProductDisplayDTO> productDisplayDTOList = products.stream()
                .map(productMapper::toProductDisplayDTO)
                .collect(Collectors.toList());
        return new SuccessDataResult<>("Products found successfully", productDisplayDTOList);
    }

    @Override
    public DataResult<List<String>> findKeywordSuggestions(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }
        return new SuccessDataResult<>("Keyword suggestions found successfully",
                productRepository.findKeywordSuggestions(prefix.trim()));
    }

    @Override
    public DataResult<Map<String, List<AttributeSummary>>> getGroupedAttributes(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }
        List<AttributeSummary> attributeStats = productRepository.findAttributeStats(categoryId);
        Map<String, List<AttributeSummary>> groupedAttributes = attributeStats.stream()
                .collect(Collectors.groupingBy(AttributeSummary::getKey));
        return new SuccessDataResult<>("Grouped attributes found successfully", groupedAttributes);
    }

    @Override
    public DataResult<Map<String, List<AttributeSummary>>> getGroupedAttributes(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("Category IDs cannot be null or empty");
        }
        List<AttributeSummary> attributeStats = productRepository.findAttributeStats(categoryIds);
        Map<String, List<AttributeSummary>> groupedAttributes = attributeStats.stream()
                .collect(Collectors.groupingBy(AttributeSummary::getKey));
        return new SuccessDataResult<>("Grouped attributes found successfully", groupedAttributes);
    }
}

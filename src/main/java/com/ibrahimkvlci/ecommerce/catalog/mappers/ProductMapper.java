package com.ibrahimkvlci.ecommerce.catalog.mappers;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;

    @Value("${bucket.image.base-url}")
    private String imageBaseUrl;

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryMapper.toEntity(productDTO.getCategoryDTO()));
        product.setBrand(brandMapper.toEntity(productDTO.getBrandDTO()));
        product.setFeatured(productDTO.isFeatured());
        return product;
    }

    public Product toEntity(ProductRequestDTO productAddDTO) {
        if (productAddDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setTitle(productAddDTO.getTitle());
        product.setDescription(productAddDTO.getDescription());
        product.setCategory(new Category(productAddDTO.getCategoryId()));
        product.setBrand(new Brand(productAddDTO.getBrandId()));
        product.setFeatured(productAddDTO.isFeatured());
        return product;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setCategoryDTO(categoryMapper.toDTO(product.getCategory()));
        dto.setBrandDTO(brandMapper.toDTO(product.getBrand()));
        dto.setFeatured(product.isFeatured());
        dto.setImagesUrl(product.getImages().stream().map(i -> imageBaseUrl + "/" + i.getImageUrl())
                .collect(Collectors.toList()));
        return dto;
    }

    public ProductDisplayDTO toProductDisplayDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDisplayDTO dto = new ProductDisplayDTO();
        dto.setProductId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setBrandName(product.getBrand().getName());
        Inventory inventory = product.getInventories().stream().min(Comparator.comparing(Inventory::getPrice)).get();
        dto.setPrice(inventory.getPrice());
        dto.setSellerId(inventory.getSellerId());
        dto.setImagesUrl(product.getImages().stream().map(i -> imageBaseUrl + "/" + i.getImageUrl())
                .collect(Collectors.toList()));
        return dto;
    }

}

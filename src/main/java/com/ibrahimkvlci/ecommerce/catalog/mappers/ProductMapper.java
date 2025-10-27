package com.ibrahimkvlci.ecommerce.catalog.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;

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
        return dto;
    }

}



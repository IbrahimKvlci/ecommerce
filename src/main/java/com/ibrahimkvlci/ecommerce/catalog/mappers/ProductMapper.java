package com.ibrahimkvlci.ecommerce.catalog.mappers;

import java.util.Comparator;
import java.util.Map;
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
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;

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
        product.setAttributes(productDTO.getAttributes());
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
        product.setAttributes(productAddDTO.getAttributes());
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
        dto.setAttributes(product.getAttributes());
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
        if (product.getInventories() != null && !product.getInventories().isEmpty()) {
            Inventory inventory = product.getInventories().stream().min(Comparator.comparing(Inventory::getPrice))
                    .get();
            dto.setPrice(inventory.getPrice());
            dto.setSellerId(inventory.getSellerId());
        }
        dto.setImagesUrl(product.getImages().stream().map(i -> imageBaseUrl + "/" + i.getImageUrl())
                .collect(Collectors.toList()));
        dto.setAttributes(product.getAttributes());
        return dto;
    }

    public ProductDisplayDTO toProductDisplayDTO(ProductDocument productDocument) {
        if (productDocument == null) {
            return null;
        }
        ProductDisplayDTO dto = new ProductDisplayDTO();
        dto.setProductId(productDocument.getId());
        dto.setTitle(productDocument.getTitle());
        dto.setDescription(productDocument.getDescription());
        Map<String, Object> attributes = productDocument.getAttributes().stream()
                .collect(Collectors.toMap(ProductDocument.AttributeItem::getKey,
                        ProductDocument.AttributeItem::getValue));
        dto.setImagesUrl(productDocument.getImages().stream().map(i -> imageBaseUrl + "/" + i)
                .collect(Collectors.toList()));
        dto.setAttributes(attributes);
        return dto;
    }

    public ProductDocument toProductDocument(Product product) {
        if (product == null) {
            return null;
        }
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(product.getId());
        productDocument.setTitle(product.getTitle());
        productDocument.setDescription(product.getDescription());
        productDocument.setAttributes(product.getAttributes().entrySet().stream()
                .map(attr -> new ProductDocument.AttributeItem(attr.getKey(), attr.getValue()))
                .collect(Collectors.toList()));
        productDocument.setImages(product.getImages().stream().map(i -> i.getImageUrl())
                .collect(Collectors.toList()));
        return productDocument;
    }

}

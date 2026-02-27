package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {

    private Page<ProductDisplayDTO> products;

    private List<CategoryDTO> categories;

    private List<AttributeDTO> attributes;

    private String categoryName;

    public ProductSearchDTO(Page<ProductDisplayDTO> products, List<CategoryDTO> categories,
            List<AttributeDTO> attributes) {
        this.products = products;
        this.categories = categories;
        this.attributes = attributes;
    }
}

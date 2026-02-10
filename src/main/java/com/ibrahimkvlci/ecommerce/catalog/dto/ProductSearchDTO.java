package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {

    private List<ProductDisplayDTO> products;

    private List<CategoryDTO> categories;

    private List<AttributeDTO> attributes;
}

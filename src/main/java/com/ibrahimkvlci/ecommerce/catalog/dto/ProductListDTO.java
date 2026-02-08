package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ibrahimkvlci.ecommerce.catalog.repositories.projection.AttributeSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

    private Page<ProductDisplayDTO> products;

    private List<CategoryDTO> categories;

    private List<AttributeSummary> attributes;
}

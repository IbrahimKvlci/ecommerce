package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

    private String searchTerm;

    private List<Long> categoryIds;

    private List<AttributeDTO> filters;

    private double minPrice = 0;

    private double maxPrice = Double.MAX_VALUE;

}

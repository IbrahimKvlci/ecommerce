package com.ibrahimkvlci.ecommerce.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueDTO {
    private String valueText;
    private Long count;
}

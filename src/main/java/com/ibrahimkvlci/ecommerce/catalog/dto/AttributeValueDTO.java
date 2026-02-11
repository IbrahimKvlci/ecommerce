package com.ibrahimkvlci.ecommerce.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueDTO {
    private String valueText;
    private Long count;
    @JsonProperty("isSelected")
    private Boolean isSelected;
}

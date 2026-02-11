package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private String key;
    private List<String> values;
}

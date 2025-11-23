package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDTO {
    private Long id;
    private String name;
    private String code;
}

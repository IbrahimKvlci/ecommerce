package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponseDTO {
    private Long id;
    private String name;
    private Long countryId;
    private String countryName;
}

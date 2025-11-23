package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictResponseDTO {
    private Long id;
    private String name;
    private Long cityId;
    private String cityName;
    private String countryName;
}

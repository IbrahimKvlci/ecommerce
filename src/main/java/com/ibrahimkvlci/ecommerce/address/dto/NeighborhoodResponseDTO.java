package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodResponseDTO {
    private Long id;
    private String name;
    private Long districtId;
    private String districtName;
    private String cityName;
    private String countryName;
}

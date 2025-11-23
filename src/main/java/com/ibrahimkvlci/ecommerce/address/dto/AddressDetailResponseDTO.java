package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailResponseDTO {

    private Long id;
    private String addressTitle;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String postalCode;
    private CountryResponseDTO country;
    private CityResponseDTO city;
    private DistrictResponseDTO district;
    private NeighborhoodResponseDTO neighborhood;
    private boolean defaultAddress;
}

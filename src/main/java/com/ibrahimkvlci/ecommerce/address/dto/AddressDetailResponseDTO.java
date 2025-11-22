package com.ibrahimkvlci.ecommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String postalCode;
    private Long countryId;
    private String countryName;
    private Long cityId;
    private String cityName;
    private Long districtId;
    private String districtName;
    private Long neighborhoodId;
    private String neighborhoodName;
    private boolean defaultAddress;
}

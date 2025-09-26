package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillAddressDetailDTO {

    @NotNull(message = "CustomerId is required")
    private Long customerId;

    @NotNull(message = "CountryCode is required")
    private String countryCode;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "District is required")
    private String district;

    @NotNull(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Postal code is required")
    private String addressPostalCode;

    @NotBlank(message = "City code is required")
    private String cityCode;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Phone is required")
    private String phone;
}

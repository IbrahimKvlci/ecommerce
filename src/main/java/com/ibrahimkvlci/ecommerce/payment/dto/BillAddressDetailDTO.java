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

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Country ID is required")
    private Long countryId;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @NotNull(message = "District ID is required")
    private Long districtId;

    @NotNull(message = "Neighborhood ID is required")
    private Long neighborhoodId;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Postal code is required")
    private String addressPostalCode;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Phone is required")
    private String phone;
}

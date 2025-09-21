package com.ibrahimkvlci.ecommerce.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Country entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name must not exceed 100 characters")
    private String name;
    
    @NotBlank(message = "Country code is required")
    @Size(max = 3, message = "Country code must not exceed 3 characters")
    private String code;
}

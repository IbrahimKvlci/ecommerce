package com.ibrahimkvlci.ecommerce.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for District entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {
    
    @NotBlank(message = "District name is required")
    @Size(max = 100, message = "District name must not exceed 100 characters")
    private String name;
    
    @NotNull(message = "City is required")
    private Long cityId;
    
    private String cityName;
    private String countryName;
}

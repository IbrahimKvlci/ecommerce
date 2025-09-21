package com.ibrahimkvlci.ecommerce.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for AddressDetail entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailDTO {
    
    @NotNull(message = "Country is required")
    private Long countryId;
    private String countryName;
    
    @NotNull(message = "City is required")
    private Long cityId;
    private String cityName;
    
    @NotNull(message = "District is required")
    private Long districtId;
    private String districtName;
    
    @NotNull(message = "Neighborhood is required")
    private Long neighborhoodId;
    private String neighborhoodName;
    
    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;
    
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;
    
    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname must not exceed 50 characters")
    private String surname;
    
    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    /**
     * Get full name (name + surname)
     */
    public String getFullName() {
        return name + " " + surname;
    }
    
    /**
     * Get formatted address string
     */
    public String getFormattedAddress() {
        return address + ", " + neighborhoodName + ", " + districtName + 
               ", " + cityName + ", " + countryName;
    }
}

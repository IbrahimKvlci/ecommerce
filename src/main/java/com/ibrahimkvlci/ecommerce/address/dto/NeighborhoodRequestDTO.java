package com.ibrahimkvlci.ecommerce.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodRequestDTO {

    @NotBlank(message = "Neighborhood name is required")
    @Size(max = 100, message = "Neighborhood name must not exceed 100 characters")
    private String name;

    @NotNull(message = "District is required")
    private Long districtId;
}

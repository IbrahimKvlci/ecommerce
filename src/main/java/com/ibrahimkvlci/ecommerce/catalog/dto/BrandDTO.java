package com.ibrahimkvlci.ecommerce.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;

    @NotBlank(message = "Brand name is required")
    @Size(min = 1, max = 100, message = "Brand name must be between 1 and 100 characters")
    private String name;
}



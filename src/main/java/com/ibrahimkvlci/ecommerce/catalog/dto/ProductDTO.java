package com.ibrahimkvlci.ecommerce.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Product operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Product title is required")
    @Size(min = 1, max = 255, message = "Product title must be between 1 and 255 characters")
    private String title;
    
    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    private Double price;
    
    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Brand is required")
    private Long brandId;
}

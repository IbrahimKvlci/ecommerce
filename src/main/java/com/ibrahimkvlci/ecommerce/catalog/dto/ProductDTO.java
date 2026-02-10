package com.ibrahimkvlci.ecommerce.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
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

    // @NotNull(message = "Category is required")
    private CategoryDTO categoryDTO;

    // @NotNull(message = "Brand is required")
    private BrandDTO brandDTO;

    private boolean featured;

    private List<String> imagesUrl;

    private Map<String, List<String>> attributes;

}

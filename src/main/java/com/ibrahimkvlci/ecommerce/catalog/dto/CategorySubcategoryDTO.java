package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySubcategoryDTO {
        
    private Long id;
    
    @NotBlank(message = "Category name is required")
    @Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters")
    private String name;

    private List<CategoryDTO> subcategories;
}

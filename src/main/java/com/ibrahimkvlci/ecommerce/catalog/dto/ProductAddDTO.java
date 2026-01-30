package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddDTO {

    @NotBlank(message = "Product title is required")
    @Size(min = 1, max = 255, message = "Product title must be between 1 and 255 characters")
    private String title;

    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Brand is required")
    private Long brandId;

    private boolean featured;
}

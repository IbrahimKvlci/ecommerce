package com.ibrahimkvlci.ecommerce.catalog.dto;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
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
    
    /**
     * Convert DTO to entity
     */
    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        return product;
    }
    
    /**
     * Create DTO from entity
     */
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
}

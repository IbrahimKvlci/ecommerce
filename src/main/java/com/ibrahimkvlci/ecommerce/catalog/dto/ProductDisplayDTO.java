package com.ibrahimkvlci.ecommerce.catalog.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing inventory details for a product.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDisplayDTO {

    private Long productId;
    
    private String title;
    
    private String description;
    
    private String brandName;

    private Double price;

    private Long sellerId;
}

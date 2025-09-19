package com.ibrahimkvlci.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    
    @NotNull(message = "Cart ID is required")
    private Long cartId;
    
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}

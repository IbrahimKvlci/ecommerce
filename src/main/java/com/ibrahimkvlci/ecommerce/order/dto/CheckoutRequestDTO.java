package com.ibrahimkvlci.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for checkout requests.
 * Used when initiating checkout from a cart.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDTO {
    
    @NotNull(message = "Cart ID is required")
    @Positive(message = "Cart ID must be positive")
    private Long cartId;
    
    private String notes;
}

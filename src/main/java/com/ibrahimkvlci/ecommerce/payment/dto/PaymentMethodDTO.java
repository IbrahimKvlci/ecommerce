package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for PaymentMethod operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    
    private Long id;
    
    @NotBlank(message = "Payment method name is required")
    @Size(min = 1, max = 100, message = "Payment method name must be between 1 and 100 characters")
    private String name;
}

package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for PaymentDetail operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDTO {
    
    private Long id;
    
    @NotNull(message = "Order ID is required")
    private Long orderNumberId;
    
    @NotNull(message = "Payment method ID is required")
    private Long paymentMethodId;
    
}

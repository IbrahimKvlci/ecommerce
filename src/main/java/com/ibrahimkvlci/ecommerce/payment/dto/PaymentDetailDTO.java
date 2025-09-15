package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    private Long orderId;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Payment method ID is required")
    private Long paymentMethodId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;
    
    private Boolean isPaid;
}

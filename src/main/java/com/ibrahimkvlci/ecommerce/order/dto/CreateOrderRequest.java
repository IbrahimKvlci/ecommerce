package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Request DTO for creating a new order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Order status is required")
    private OrderStatus status;
    
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
    
    @NotNull(message = "Order items are required")
    @Size(min = 1, message = "At least one order item is required")
    @Valid
    private List<CreateOrderItemRequest> orderItems;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderItemRequest {
        
        @NotNull(message = "Product ID is required")
        private Long productId;
        
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;
        
        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be positive")
        private Double unitPrice;
    }
}

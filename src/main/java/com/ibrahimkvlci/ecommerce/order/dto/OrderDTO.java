package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Data Transfer Object for Order operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    
    private String orderNumber;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    private String customerName;
    
    @NotNull(message = "Order status is required")
    private OrderStatus status;
    
    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;
    
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private List<OrderItemDTO> orderItems;
}

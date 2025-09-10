package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating an existing order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    
    private OrderStatus status;
    
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
}

package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for checkout responses.
 * Contains order information after successful checkout.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponseDTO {
    
    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private OrderStatus status;
    private Double totalAmount;
    private String notes;
    private LocalDateTime createdAt;
    private String message;
    
    public CheckoutResponseDTO(OrderDTO orderDTO, String message) {
        this.orderId = orderDTO.getId();
        this.orderNumber = orderDTO.getOrderNumber();
        this.customerId = orderDTO.getCustomerId();
        this.status = orderDTO.getStatus();
        this.totalAmount = orderDTO.getTotalAmount();
        this.notes = orderDTO.getNotes();
        this.createdAt = orderDTO.getCreatedAt();
        this.message = message;
    }
}

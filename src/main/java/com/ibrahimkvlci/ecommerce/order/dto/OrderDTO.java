package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    
    /**
     * Convert DTO to entity
     */
    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setOrderNumber(this.orderNumber);
        order.setStatus(this.status);
        order.setTotalAmount(this.totalAmount);
        order.setNotes(this.notes);
        order.setCreatedAt(this.createdAt);
        order.setUpdatedAt(this.updatedAt);
        
        if (this.customerId != null) {
            Customer customer = new Customer();
            customer.setId(this.customerId);
        }
        
        if (this.orderItems != null) {
            order.setOrderItems(this.orderItems.stream()
                    .map(OrderItemDTO::toEntity)
                    .collect(Collectors.toList()));
        }
        
        return order;
    }
    
    /**
     * Create DTO from entity
     */
    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setNotes(order.getNotes());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        if (order.getOrderItems() != null) {
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(OrderItemDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}

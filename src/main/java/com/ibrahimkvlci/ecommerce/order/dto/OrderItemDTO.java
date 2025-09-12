package com.ibrahimkvlci.ecommerce.order.dto;

import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for OrderItem operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    
    private Long id;
    
    private ProductDTO product;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;
    
    private Double totalPrice;
    
    /**
     * Convert DTO to entity
     */
    public OrderItem toEntity() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(this.id);
        orderItem.setQuantity(this.quantity);
        orderItem.setUnitPrice(this.unitPrice);
        orderItem.setTotalPrice(this.totalPrice);
        orderItem.setProductId(this.product.getId());
        
        return orderItem;
    }
    
    /**
     * Create DTO from entity
     */
    public static OrderItemDTO fromEntity(OrderItem orderItem, ProductDTO product) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotalPrice(orderItem.getTotalPrice());
        dto.setProduct(product);
        
        return dto;
    }
}

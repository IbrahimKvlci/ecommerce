package com.ibrahimkvlci.ecommerce.order.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull(message = "Order is required")
    private Order order;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @Column(name = "unit_price", nullable = false)
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;
    
    @Column(name = "total_price", nullable = false)
    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;
    
    @PrePersist
    @PreUpdate
    protected void calculateTotalPrice() {
        if (quantity != null && unitPrice != null) {
            totalPrice = quantity * unitPrice;
        }
    }
}

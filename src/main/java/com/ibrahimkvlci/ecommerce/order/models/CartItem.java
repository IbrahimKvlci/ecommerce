package com.ibrahimkvlci.ecommerce.order.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_id")
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Column(name = "quantity")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "created_at")
    @NotNull(message = "Created at is required")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull(message = "Updated at is required")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.totalPrice = this.price * this.quantity;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.totalPrice = this.price * this.quantity;
    }
}

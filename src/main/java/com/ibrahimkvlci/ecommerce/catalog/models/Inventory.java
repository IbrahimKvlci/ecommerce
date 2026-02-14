package com.ibrahimkvlci.ecommerce.catalog.models;

import com.ibrahimkvlci.ecommerce.catalog.models.id.InventoryId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(InventoryId.class)
public class Inventory {

    @Id
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discount_price", nullable = true)
    private Double discountPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Inventory(Long sellerId, Product product, Double price, Integer quantity) {
        this.sellerId = sellerId;
        this.product = product;
        this.price = price;
        this.discountPrice = null;
        this.quantity = quantity;
    }

}

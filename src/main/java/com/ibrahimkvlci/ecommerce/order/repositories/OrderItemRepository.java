package com.ibrahimkvlci.ecommerce.order.repositories;

import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Find order items by order
     */
    List<OrderItem> findByOrder(Order order);
    
    /**
     * Find order items by order ID
     */
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * Find order items by product ID
     */
    List<OrderItem> findByProductId(Long productId);
    
    
    /**
     * Find order items by order ID and product ID
     */
    List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);

    /**
     * Find order items with quantity greater than given value
     */
    List<OrderItem> findByQuantityGreaterThan(Integer quantity);
    
    /**
     * Find order items with unit price greater than given value
     */
    List<OrderItem> findByUnitPriceGreaterThan(Double unitPrice);
    
    /**
     * Find order items with total price greater than given value
     */
    List<OrderItem> findByTotalPriceGreaterThan(Double totalPrice);
}

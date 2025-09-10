package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CreateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    
    /**
     * Create a new order
     */
    OrderDTO createOrder(CreateOrderRequest request);
    
    /**
     * Get all orders
     */
    List<OrderDTO> getAllOrders();
    
    /**
     * Get order by ID
     */
    Optional<OrderDTO> getOrderById(Long id);
    
    /**
     * Get order by order number
     */
    Optional<OrderDTO> getOrderByOrderNumber(String orderNumber);
    
    /**
     * Update an existing order
     */
    OrderDTO updateOrder(Long id, UpdateOrderRequest request);
    
    /**
     * Delete an order
     */
    void deleteOrder(Long id);
    
    /**
     * Get orders by customer ID
     */
    List<OrderDTO> getOrdersByCustomerId(Long customerId);
    
    /**
     * Get orders by status
     */
    List<OrderDTO> getOrdersByStatus(OrderStatus status);
    
    /**
     * Update order status
     */
    OrderDTO updateOrderStatus(Long id, OrderStatus status);
    
    /**
     * Cancel an order
     */
    OrderDTO cancelOrder(Long id);
    
    /**
     * Get orders by date range
     */
    List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get orders by total amount range
     */
    List<OrderDTO> getOrdersByAmountRange(Double minAmount, Double maxAmount);
    
    /**
     * Get customer order history
     */
    List<OrderDTO> getCustomerOrderHistory(Long customerId);
    
    /**
     * Calculate order total
     */
    Double calculateOrderTotal(List<CreateOrderRequest.CreateOrderItemRequest> orderItems);
    
    /**
     * Check if order can be cancelled
     */
    boolean canCancelOrder(Long id);
    
    /**
     * Check if order can be updated
     */
    boolean canUpdateOrder(Long id);
    
}

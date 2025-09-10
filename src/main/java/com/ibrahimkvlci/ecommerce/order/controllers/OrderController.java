package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CreateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.order.services.OrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Order operations.
 * Provides endpoints for CRUD operations and order management.
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    /**
     * Create a new order
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDTO order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    /**
     * Get all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get order by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get order by order number
     */
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing order
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, 
                                              @Valid @RequestBody UpdateOrderRequest request) {
        OrderDTO order = orderService.updateOrder(id, request);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Delete an order
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get orders by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get orders by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Update order status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, 
                                                    @RequestParam OrderStatus status) {
        OrderDTO order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Cancel an order
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long id) {
        OrderDTO order = orderService.cancelOrder(id);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Get orders by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam LocalDateTime startDate, 
            @RequestParam LocalDateTime endDate) {
        List<OrderDTO> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get orders by amount range
     */
    @GetMapping("/amount-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByAmountRange(
            @RequestParam Double minAmount, 
            @RequestParam Double maxAmount) {
        List<OrderDTO> orders = orderService.getOrdersByAmountRange(minAmount, maxAmount);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get customer order history
     */
    @GetMapping("/customer/{customerId}/history")
    public ResponseEntity<List<OrderDTO>> getCustomerOrderHistory(@PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.getCustomerOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Check if order can be cancelled
     */
    @GetMapping("/{id}/can-cancel")
    public ResponseEntity<Boolean> canCancelOrder(@PathVariable Long id) {
        boolean canCancel = orderService.canCancelOrder(id);
        return ResponseEntity.ok(canCancel);
    }
    
    /**
     * Check if order can be updated
     */
    @GetMapping("/{id}/can-update")
    public ResponseEntity<Boolean> canUpdateOrder(@PathVariable Long id) {
        boolean canUpdate = orderService.canUpdateOrder(id);
        return ResponseEntity.ok(canUpdate);
    }
}

package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CreateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.order.services.OrderService;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

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
@RequestMapping("/api/orders/orders")
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
    public ResponseEntity<DataResult<OrderDTO>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    /**
     * Get all orders
     */
    @GetMapping
    public ResponseEntity<DataResult<List<OrderDTO>>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Get order by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResult<OrderDTO>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * Get order by order number
     */
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<DataResult<OrderDTO>> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }

    /**
     * Update an existing order
     */
    @PutMapping("/{id}")
    public ResponseEntity<DataResult<OrderDTO>> updateOrder(@PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    /**
     * Delete an order
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @GetMapping("/customer")
    public ResponseEntity<DataResult<List<OrderDTO>>> getOrdersOfCustomer() {
        return ResponseEntity.ok(orderService.getOrdersOfCustomer());
    }

    /**
     * Get orders by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<DataResult<List<OrderDTO>>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * Get orders by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<DataResult<List<OrderDTO>>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    /**
     * Update order status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<DataResult<OrderDTO>> updateOrderStatus(@PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    /**
     * Cancel an order
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<DataResult<OrderDTO>> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    /**
     * Get orders by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<DataResult<List<OrderDTO>>> getOrdersByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.getOrdersByDateRange(startDate, endDate));
    }

    /**
     * Get orders by amount range
     */
    @GetMapping("/amount-range")
    public ResponseEntity<DataResult<List<OrderDTO>>> getOrdersByAmountRange(
            @RequestParam Double minAmount,
            @RequestParam Double maxAmount) {
        return ResponseEntity.ok(orderService.getOrdersByAmountRange(minAmount, maxAmount));
    }

    /**
     * Get customer order history
     */
    @GetMapping("/customer/{customerId}/history")
    public ResponseEntity<DataResult<List<OrderDTO>>> getCustomerOrderHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getCustomerOrderHistory(customerId));
    }

    /**
     * Check if order can be cancelled
     */
    @GetMapping("/{id}/can-cancel")
    public ResponseEntity<DataResult<Boolean>> canCancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.canCancelOrder(id));
    }

    /**
     * Check if order can be updated
     */
    @GetMapping("/{id}/can-update")
    public ResponseEntity<DataResult<Boolean>> canUpdateOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.canUpdateOrder(id));
    }
}

package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CreateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;

import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    /**
     * Create a new order
     */
    DataResult<OrderDTO> createOrder(CreateOrderRequest request);

    /**
     * Get all orders
     */
    DataResult<List<OrderDTO>> getAllOrders();

    /**
     * Get order by ID
     */
    DataResult<OrderDTO> getOrderById(Long id);

    /**
     * Get order by order number
     */
    DataResult<OrderDTO> getOrderByOrderNumber(String orderNumber);

    /**
     * Update an existing order
     */
    DataResult<OrderDTO> updateOrder(Long id, UpdateOrderRequest request);

    /**
     * Delete an order
     */
    Result deleteOrder(Long id);

    /**
     * Get orders of customer
     */
    DataResult<List<OrderDTO>> getOrdersOfCustomer();

    /**
     * Get orders by customer ID
     */
    DataResult<List<OrderDTO>> getOrdersByCustomerId(Long customerId);

    /**
     * Get orders by status
     */
    DataResult<List<OrderDTO>> getOrdersByStatus(OrderStatus status);

    /**
     * Update order status
     */
    DataResult<OrderDTO> updateOrderStatus(Long id, OrderStatus status);

    /**
     * Cancel an order
     */
    DataResult<OrderDTO> cancelOrder(Long id);

    /**
     * Get orders by date range
     */
    DataResult<List<OrderDTO>> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get orders by total amount range
     */
    DataResult<List<OrderDTO>> getOrdersByAmountRange(Double minAmount, Double maxAmount);

    /**
     * Get customer order history
     */
    DataResult<List<OrderDTO>> getCustomerOrderHistory(Long customerId);

    /**
     * Calculate order total
     */
    DataResult<Double> calculateOrderTotal(List<CreateOrderRequest.CreateOrderItemRequest> orderItems);

    /**
     * Check if order can be cancelled
     */
    DataResult<Boolean> canCancelOrder(Long id);

    /**
     * Check if order can be updated
     */
    DataResult<Boolean> canUpdateOrder(Long id);

    /**
     * Map order to DTO
     */
    OrderDTO mapToDTO(Order order);

    /**
     * Map order DTO to entity
     */
    Order mapToEntity(OrderDTO orderDTO);
}

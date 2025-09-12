package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.client.CategoryClient;
import com.ibrahimkvlci.ecommerce.order.client.CustomerClient;
import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.CreateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateOrderRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderStatusException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderValidationException;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.order.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

import com.ibrahimkvlci.ecommerce.order.repositories.OrderItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;
    
    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        
        // Calculate total amount
        Double totalAmount = calculateOrderTotal(request.getOrderItems());
        
        // Create order
        Order order = new Order();
        order.setTotalAmount(totalAmount);
        order.setNotes(request.getNotes()); 

        if(!customerClient.existsById(request.getCustomerId())){
            throw new OrderValidationException("Customer not available with ID: " + request.getCustomerId());
        }

        order.setCustomerId(request.getCustomerId());

        Order savedOrder = orderRepository.save(order);
        
        // Create order items
        List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(itemRequest -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(savedOrder);
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setUnitPrice(itemRequest.getUnitPrice());
                    orderItem.setTotalPrice(itemRequest.getQuantity() * itemRequest.getUnitPrice());

                    if(!productClient.isProductAvailable(itemRequest.getProductId())){
                        throw new OrderValidationException("Product not available with ID: " + itemRequest.getProductId());
                    }

                    orderItem.setProductId(itemRequest.getProductId());
                    
                    return orderItem;
                })
                .collect(Collectors.toList());
        
        orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItems(orderItems);
        
        return OrderDTO.fromEntity(savedOrder);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderDTO::fromEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(OrderDTO::fromEntity);
    }
    
    @Override
    public OrderDTO updateOrder(Long id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        if (!canUpdateOrder(id)) {
            throw new OrderStatusException(order.getStatus(), "update");
        }
        
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getNotes() != null) {
            order.setNotes(request.getNotes());
        }
        
        Order updatedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(updatedOrder);
    }
    
    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        if (!canUpdateOrder(id)) {
            throw new OrderStatusException(order.getStatus(), "delete");
        }
        
        // Delete order items first
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
        orderItemRepository.deleteAll(orderItems);
        orderRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        OrderStatus currentStatus = order.getStatus();
        
        // Validate status transition
        if (!isValidStatusTransition(currentStatus, status)) {
            throw new OrderStatusException(currentStatus, status);
        }
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(updatedOrder);
    }
    
    @Override
    public OrderDTO cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        if (!canCancelOrder(id)) {
            throw new OrderStatusException(order.getStatus(), "cancel");
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(updatedOrder);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByAmountRange(Double minAmount, Double maxAmount) {
        return orderRepository.findByTotalAmountBetween(minAmount, maxAmount).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getCustomerOrderHistory(Long customerId) {
        return getOrdersByCustomerId(customerId);
    }
    
    @Override
    public Double calculateOrderTotal(List<CreateOrderRequest.CreateOrderItemRequest> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean canCancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        return order.getStatus() == OrderStatus.PENDING || 
               order.getStatus() == OrderStatus.CONFIRMED;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean canUpdateOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        
        return order.getStatus() == OrderStatus.PENDING || 
               order.getStatus() == OrderStatus.CONFIRMED;
    }
    
    private boolean isValidStatusTransition(OrderStatus current, OrderStatus target) {
        // Define valid status transitions
        switch (current) {
            case PENDING:
                return target == OrderStatus.CONFIRMED || target == OrderStatus.CANCELLED;
            case CONFIRMED:
                return target == OrderStatus.PROCESSING || target == OrderStatus.CANCELLED;
            case PROCESSING:
                return target == OrderStatus.SHIPPED || target == OrderStatus.CANCELLED;
            case SHIPPED:
                return target == OrderStatus.DELIVERED;
            case DELIVERED:
                return target == OrderStatus.REFUNDED;
            case CANCELLED:
            case REFUNDED:
                return false; // Terminal states
            default:
                return false;
        }
    }
}

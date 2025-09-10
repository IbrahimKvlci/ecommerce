package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when an order item is not found.
 */
public class OrderItemNotFoundException extends RuntimeException {
    
    public OrderItemNotFoundException(String message) {
        super(message);
    }
    
    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderItemNotFoundException(Long orderItemId) {
        super("Order item not found with ID: " + orderItemId);
    }
    
    public OrderItemNotFoundException(Long orderId, Long productId) {
        super("Order item not found for order ID: " + orderId + " and product ID: " + productId);
    }
}

package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends RuntimeException {
    
    public OrderNotFoundException(String message) {
        super(message);
    }
    
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderNotFoundException(Long orderId) {
        super("Order not found with ID: " + orderId);
    }
    
    public OrderNotFoundException(String orderNumber, boolean isOrderNumber) {
        super("Order not found with order number: " + orderNumber);
    }
}

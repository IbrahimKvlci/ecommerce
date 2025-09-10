package com.ibrahimkvlci.ecommerce.order.exceptions;

import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;

/**
 * Exception thrown when an invalid order status operation is attempted.
 */
public class OrderStatusException extends RuntimeException {
    
    public OrderStatusException(String message) {
        super(message);
    }
    
    public OrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderStatusException(OrderStatus currentStatus, OrderStatus targetStatus) {
        super("Cannot change order status from " + currentStatus + " to " + targetStatus);
    }
    
    public OrderStatusException(OrderStatus currentStatus, String operation) {
        super("Cannot perform operation '" + operation + "' on order with status: " + currentStatus);
    }
}

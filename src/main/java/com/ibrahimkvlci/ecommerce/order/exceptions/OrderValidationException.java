package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when order validation fails.
 */
public class OrderValidationException extends RuntimeException {
    
    public OrderValidationException(String message) {
        super(message);
    }
    
    public OrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderValidationException(String field, String reason) {
        super("Order validation failed for field '" + field + "': " + reason);
    }
}

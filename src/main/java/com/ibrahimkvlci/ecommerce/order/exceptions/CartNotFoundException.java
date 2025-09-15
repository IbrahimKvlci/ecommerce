package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when a cart is not found.
 */
public class CartNotFoundException extends RuntimeException {
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CartNotFoundException(Long cartId) {
        super("Cart not found with ID: " + cartId);
    }
    
    public CartNotFoundException(Long customerId, boolean isCustomerId) {
        super("Cart not found for customer ID: " + customerId);
    }
}

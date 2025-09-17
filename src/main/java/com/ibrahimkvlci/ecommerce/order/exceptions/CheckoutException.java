package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when checkout operations fail.
 * This includes scenarios like product unavailability, insufficient inventory, etc.
 */
public class CheckoutException extends RuntimeException {
    
    public CheckoutException(String message) {
        super(message);
    }
    
    public CheckoutException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CheckoutException(Long cartId, String reason) {
        super("Checkout failed for cart ID " + cartId + ": " + reason);
    }
    
    public CheckoutException(Long orderId, String reason, boolean isOrderId) {
        super("Checkout completion failed for order ID " + orderId + ": " + reason);
    }
}

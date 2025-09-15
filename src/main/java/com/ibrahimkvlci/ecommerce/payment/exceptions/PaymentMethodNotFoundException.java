package com.ibrahimkvlci.ecommerce.payment.exceptions;

/**
 * Exception thrown when a payment method is not found.
 */
public class PaymentMethodNotFoundException extends RuntimeException {
    
    public PaymentMethodNotFoundException(String message) {
        super(message);
    }
    
    public PaymentMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

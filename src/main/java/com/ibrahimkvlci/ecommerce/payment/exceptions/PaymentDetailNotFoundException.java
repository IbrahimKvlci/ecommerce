package com.ibrahimkvlci.ecommerce.payment.exceptions;

/**
 * Exception thrown when a payment detail is not found.
 */
public class PaymentDetailNotFoundException extends RuntimeException {
    
    public PaymentDetailNotFoundException(String message) {
        super(message);
    }
    
    public PaymentDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

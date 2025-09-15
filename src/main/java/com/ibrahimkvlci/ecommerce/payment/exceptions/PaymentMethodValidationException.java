package com.ibrahimkvlci.ecommerce.payment.exceptions;

/**
 * Exception thrown when payment method validation fails.
 */
public class PaymentMethodValidationException extends RuntimeException {
    
    public PaymentMethodValidationException(String message) {
        super(message);
    }
    
    public PaymentMethodValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ibrahimkvlci.ecommerce.payment.exceptions;

/**
 * Exception thrown when payment detail validation fails.
 */
public class PaymentDetailValidationException extends RuntimeException {
    
    public PaymentDetailValidationException(String message) {
        super(message);
    }
    
    public PaymentDetailValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

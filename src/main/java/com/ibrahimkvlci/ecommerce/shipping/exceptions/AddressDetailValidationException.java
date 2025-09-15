package com.ibrahimkvlci.ecommerce.shipping.exceptions;

/**
 * Exception thrown when address detail validation fails.
 */
public class AddressDetailValidationException extends RuntimeException {
    
    public AddressDetailValidationException(String message) {
        super(message);
    }
    
    public AddressDetailValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

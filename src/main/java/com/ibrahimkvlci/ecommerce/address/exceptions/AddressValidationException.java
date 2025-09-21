package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when address validation fails
 */
public class AddressValidationException extends RuntimeException {
    
    public AddressValidationException(String message) {
        super(message);
    }
    
    public AddressValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AddressValidationException(String field, String reason) {
        super("Address validation failed for field '" + field + "': " + reason);
    }
}

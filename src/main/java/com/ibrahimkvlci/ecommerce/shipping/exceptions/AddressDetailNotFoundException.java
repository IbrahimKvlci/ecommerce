package com.ibrahimkvlci.ecommerce.shipping.exceptions;

/**
 * Exception thrown when an AddressDetail is not found.
 */
public class AddressDetailNotFoundException extends RuntimeException {
    
    public AddressDetailNotFoundException(Long id) {
        super("Address detail not found with ID: " + id);
    }
    
    public AddressDetailNotFoundException(String message) {
        super(message);
    }
    
    public AddressDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when an address is not found
 */
public class AddressNotFoundException extends RuntimeException {
    
    public AddressNotFoundException(String message) {
        super(message);
    }
    
    public AddressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AddressNotFoundException(Long addressId) {
        super("Address with ID " + addressId + " not found");
    }
}

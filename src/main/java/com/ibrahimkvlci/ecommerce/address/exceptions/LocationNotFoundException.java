package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when a location (country, city, district, neighborhood) is not found
 */
public class LocationNotFoundException extends RuntimeException {
    
    public LocationNotFoundException(String message) {
        super(message);
    }
    
    public LocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public LocationNotFoundException(String locationType, Long id) {
        super(locationType + " with ID " + id + " not found");
    }
}

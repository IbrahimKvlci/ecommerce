package com.ibrahimkvlci.ecommerce.catalog.exceptions;

/**
 * Exception thrown when category validation fails.
 */
public class CategoryValidationException extends RuntimeException {
    
    public CategoryValidationException(String message) {
        super(message);
    }
    
    public CategoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ibrahimkvlci.ecommerce.catalog.exceptions;

/**
 * Exception thrown when a product is not found in the system.
 */
public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ProductNotFoundException(Long productId) {
        super("Product with ID " + productId + " not found");
    }
}

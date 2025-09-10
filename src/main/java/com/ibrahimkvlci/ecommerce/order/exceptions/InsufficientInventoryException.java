package com.ibrahimkvlci.ecommerce.order.exceptions;

/**
 * Exception thrown when there is insufficient inventory for an order item.
 */
public class InsufficientInventoryException extends RuntimeException {
    
    public InsufficientInventoryException(String message) {
        super(message);
    }
    
    public InsufficientInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InsufficientInventoryException(Long productId, Integer requestedQuantity, Integer availableQuantity) {
        super("Insufficient inventory for product ID " + productId + 
              ". Requested: " + requestedQuantity + ", Available: " + availableQuantity);
    }
    
    public InsufficientInventoryException(String productName, Integer requestedQuantity, Integer availableQuantity) {
        super("Insufficient inventory for product '" + productName + 
              "'. Requested: " + requestedQuantity + ", Available: " + availableQuantity);
    }
}

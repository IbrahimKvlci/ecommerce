package com.ibrahimkvlci.ecommerce.shipping.exceptions;

/**
 * Exception thrown when a ShipmentDetail is not found.
 */
public class ShipmentDetailNotFoundException extends RuntimeException {
    
    public ShipmentDetailNotFoundException(Long id) {
        super("Shipment detail not found with ID: " + id);
    }
    
    public ShipmentDetailNotFoundException(String message) {
        super(message);
    }
    
    public ShipmentDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

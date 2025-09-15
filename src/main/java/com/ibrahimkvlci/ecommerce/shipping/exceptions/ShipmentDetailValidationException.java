package com.ibrahimkvlci.ecommerce.shipping.exceptions;

public class ShipmentDetailValidationException extends RuntimeException {
    
    public ShipmentDetailValidationException(String message) {
        super(message);
    }
    
    public ShipmentDetailValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

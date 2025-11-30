package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when a district is not found
 */
public class DistrictNotFoundException extends LocationNotFoundException {

    public DistrictNotFoundException(String message) {
        super(message);
    }

    public DistrictNotFoundException(Long id) {
        super("District", id);
    }
}

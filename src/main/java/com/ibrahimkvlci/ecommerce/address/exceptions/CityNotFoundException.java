package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when a city is not found
 */
public class CityNotFoundException extends LocationNotFoundException {

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        super("City", id);
    }
}

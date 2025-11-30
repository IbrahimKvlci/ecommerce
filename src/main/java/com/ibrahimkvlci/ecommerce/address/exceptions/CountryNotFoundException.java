package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when a country is not found
 */
public class CountryNotFoundException extends LocationNotFoundException {

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(Long id) {
        super("Country", id);
    }
}

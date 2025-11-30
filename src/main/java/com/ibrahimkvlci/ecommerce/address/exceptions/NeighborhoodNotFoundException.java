package com.ibrahimkvlci.ecommerce.address.exceptions;

/**
 * Exception thrown when a neighborhood is not found
 */
public class NeighborhoodNotFoundException extends LocationNotFoundException {

    public NeighborhoodNotFoundException(String message) {
        super(message);
    }

    public NeighborhoodNotFoundException(Long id) {
        super("Neighborhood", id);
    }
}

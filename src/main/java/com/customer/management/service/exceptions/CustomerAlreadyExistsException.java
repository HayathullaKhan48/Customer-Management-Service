package com.customer.management.service.exceptions;

/**
 * Exception thrown when a customer already exists.
 */
public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}

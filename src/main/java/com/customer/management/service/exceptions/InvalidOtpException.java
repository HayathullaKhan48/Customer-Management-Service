package com.customer.management.service.exceptions;

/**
 * Exception thrown when an invalid OTP is provided.
 */
public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}

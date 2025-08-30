package com.customer.management.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utility class for encoding and validating passwords using BCrypt hashing.
 */
@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password into a secure hashed format.
     *
     * @param raw the raw password to encode
     * @return the encoded (hashed) password
     */
    public String encode(String raw) {
        return encoder.encode(raw);
    }

    /**
     * Validates a raw password against an encoded password.
     *
     * @param raw     the raw password to validate
     * @param encoded the hashed password to compare against
     * @return true if the raw password matches the encoded one, false otherwise
     */
    public boolean matches(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}

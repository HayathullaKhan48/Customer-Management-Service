package com.customer.management.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PasswordUtil is responsible for encoding and validating passwords
 * using BCrypt hashing algorithm.
 */
@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password into a secure hashed format.
     *
     * @param raw the raw password
     * @return encoded password
     */
    public String encode(String raw) {
        return encoder.encode(raw);
    }

    /**
     * Checks if the raw password matches the encoded password.
     *
     * @param raw     raw password
     * @param encoded encoded password
     * @return true if matches, else false
     */
    public boolean matches(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}

package com.customer.management.service.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * PasswordUtil is responsible for encoding passwords
 * using SHA-256 hashing algorithm without Spring Security.
 */
@Component
public class PasswordUtil {

    /**
     * Encodes a raw password into a secure hashed format using SHA-256.
     */
    public String encodePassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    /**
     * Compares raw password with an already hashed password.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        String hashed = encodePassword(rawPassword);
        return hashed.equals(encodedPassword);
    }
}

package com.customer.management.service.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;

import static com.customer.management.service.constant.CustomerConstant.PASSWORD_CHARACTERS;
import static com.customer.management.service.constant.CustomerConstant.PASSWORD_LENGTH;

/**
 * PasswordUtil is responsible for encoding passwords
 * using SHA-256 hashing algorithm without Spring Security.
 */
public class PasswordUtil {

    /**
     * Encodes a raw password into a secure hashed format using SHA-256.
     */
    public static String encodePassword(String rawPassword) {
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
    public static boolean matches(String rawPassword, String encodedPassword) {
        String hashed = encodePassword(rawPassword);
        return hashed.equals(encodedPassword);
    }

    private static String generatePassword() {
        SecureRandom random = new SecureRandom();
        return random.ints(PASSWORD_LENGTH, 0, PASSWORD_CHARACTERS.length())
                .mapToObj(PASSWORD_CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * Hashes the given password using SHA-256 algorithm.
     *
     * @return hashed password in hexadecimal format
     */
    public static String autoGenerateHashPassword() {
        String plainPassword = generatePassword();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
        }
    }
}

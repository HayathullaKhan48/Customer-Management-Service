package com.customer.management.service.constant;

/**
 * CustomerConstant holds all constant values used across the application.
 * 🔹 Why this class?
 *   - Centralizes all reusable values (like OTP length, password rules, etc.)
 *   - Avoids magic numbers or hardcoded strings in business logic
 *   - Makes it easier to update values in one place instead of searching the whole project
 */
public class CustomerConstant {

    /**
     * Allowed characters for generating secure random passwords.
     * Contains:
     *   - Uppercase letters (A–Z)
     *   - Lowercase letters (a–z)
     *   - Digits (0–9)
     *   - Special characters (@, #, %)
     * This ensures generated passwords are strong and hard to guess.
     * Length of the auto-generated password.
     * Currently set to 12 characters, which is considered secure.
     * Allowed characters for OTP (One-Time Password).
     * Only numeric digits (0–9) are included for simplicity.
     */
    public static final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#%";
    public static final int PASSWORD_LENGTH = 12;
    public static final String OTP_CHARACTERS = "0123456789";
    public static final int OTP_LENGTH = 6;
    /**
     * Private constructor to prevent instantiation.
     * This is a utility class meant only for holding constants.
     * */

}

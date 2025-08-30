package com.customer.management.service.util;

import com.customer.management.service.constant.CustomerConstant;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

/**
 * Utility class for customer-related operations such as generating random passwords.
 */
@Component
public class CustomerUtil {

    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a strong random password.
     * Uses characters defined in {@link CustomerConstant#PASSWORD_CHARACTERS}
     * and generates a password of length {@link CustomerConstant#PASSWORD_LENGTH}.
     *
     * @return a randomly generated strong password
     */
    public String generatePassword() {
        StringBuilder password = new StringBuilder(CustomerConstant.PASSWORD_LENGTH);
        for (int i = 0; i < CustomerConstant.PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CustomerConstant.PASSWORD_CHARACTERS.length());
            password.append(CustomerConstant.PASSWORD_CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}

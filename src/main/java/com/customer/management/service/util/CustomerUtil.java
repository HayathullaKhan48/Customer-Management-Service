package com.customer.management.service.util;

import com.customer.management.service.constant.CustomerConstant;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * CustomerUtil is used for generating random passwords.
 */
@Component
public class CustomerUtil {

    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a strong random password using defined characters in CustomerConstant.
     *
     * @return generated password
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

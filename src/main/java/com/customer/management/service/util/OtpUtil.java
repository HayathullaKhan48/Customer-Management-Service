package com.customer.management.service.util;

import com.customer.management.service.constant.CustomerConstant;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * OtpUtil is responsible for generating numeric OTP codes.
 */
@Component
public class OtpUtil {

    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a numeric OTP of fixed length defined in CustomerConstant.
     *
     * @return generated OTP
     */
    public String generateOtp() {
        StringBuilder otp = new StringBuilder(CustomerConstant.OTP_LENGTH);
        for (int i = 0; i < CustomerConstant.OTP_LENGTH; i++) {
            int index = random.nextInt(CustomerConstant.OTP_CHARACTERS.length());
            otp.append(CustomerConstant.OTP_CHARACTERS.charAt(index));
        }
        return otp.toString();
    }
}

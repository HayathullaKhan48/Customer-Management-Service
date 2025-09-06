package com.customer.management.service.util;

import com.customer.management.service.constant.CustomerConstant;
import java.security.SecureRandom;

/**
 * Utility class for generating numeric OTPs for customer verification.
 */
public class OtpUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a numeric OTP.
     * Uses characters defined in {@link CustomerConstant#OTP_CHARACTERS}
     * and generates an OTP of length {@link CustomerConstant#OTP_LENGTH}.
     *
     * @return a randomly generated numeric OTP
     */
    public static String generateOtp() {
        StringBuilder otp = new StringBuilder(CustomerConstant.OTP_LENGTH);
        for (int i = 0; i < CustomerConstant.OTP_LENGTH; i++) {
            int index = random.nextInt(CustomerConstant.OTP_CHARACTERS.length());
            otp.append(CustomerConstant.OTP_CHARACTERS.charAt(index));
        }
        return otp.toString();
    }
}

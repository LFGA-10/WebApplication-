package com.downloader.skillswitch.util;

import java.security.SecureRandom;

/**
 * Utility class for generating One-Time Passwords (OTPs) for Two-Factor Authentication (2FA).
 * Utilizes cryptographically strong random number generators to ensure the unpredictability
 * of the generated OTPs, preventing easy guessing or brute-force attacks.
 */
public class OtpUtil {

    private static final int OTP_LENGTH = 6;
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a numeric OTP of 6 digits securely.
     * This method leverages java.security.SecureRandom to comply with security standards
     * for authenticators and 2FA tokens.
     *
     * @return A cryptographically secure, randomly generated 6-digit OTP as a String.
     */
    public static String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }
}

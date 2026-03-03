package com.downloader.skillswitch.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for security features including password hashing and input sanitization.
 * Provides basic defenses against XSS and stores passwords securely.
 */
public class SecurityUtil {

    /**
     * Hashes a password using SHA-256 for secure storage.
     * 
     * @param password The plain text password.
     * @return The Base64 encoded SHA-256 hash.
     */
    public static String hashPassword(String password) {
        if (password == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Sanitizes user input to prevent Cross-Site Scripting (XSS) attacks.
     * Escapes standard HTML special characters.
     * 
     * @param input Raw input string from the user.
     * @return Sanitized string safe for rendering in HTML.
     */
    public static String sanitize(String input) {
        if (input == null) return null;
        return input.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
    }
}

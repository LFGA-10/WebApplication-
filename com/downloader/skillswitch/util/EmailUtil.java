package com.downloader.skillswitch.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Utility class for managing email transmissions, heavily used for sending OTP codes in Two-Factor Authentication.
 */
public class EmailUtil {

    /**
     * Sends an OTP to the specified email address using Gmail's SMTP server.
     * Ensure to replace placeholder email and app password with active credentials.
     *
     * @param toEmail The destination email address to receive the OTP.
     * @param otp     The generated 6-digit OTP string.
     * @throws Exception if sending the email fails.
     */
    public static void sendOTP(String toEmail, String otp) throws Exception {

        final String fromEmail = "your_email@gmail.com";
        final String password = "your_app_password";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
        message.setSubject("Your Login OTP");
        message.setText("Your OTP is: " + otp + "\nIt expires in 5 minutes.");

        Transport.send(message);
    }
}

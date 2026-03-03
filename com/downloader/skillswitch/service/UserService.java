package com.downloader.skillswitch.service;

import com.downloader.skillswitch.model.User;
import com.downloader.skillswitch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;

/**
 * Service class for handling user-related operations, including authentication,
 * registration, and Two-Factor Authentication (OTP) management.
 */
public class UserService {

    /**
     * @see  com.downloader.skillswitch.model.User#getName getName method
     * @param user takes user object
     */
    public void saveUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user securely using an email and password.
     *
     * @param email    The user's registered email address.
     * @param password The user's password.
     * @return The authenticated User object if credentials are correct, null otherwise.
     */
    public User login(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM User WHERE email = :email AND password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        }
    }

    /**
     * Saves a generated OTP to the database for a specific user to be used in Two-Factor Authentication.
     *
     * @param userId The ID of the authenticated user.
     * @param otp    The 6-digit OTP string.
     * @param expiry The time after which the OTP will become invalid.
     */
    public void saveOtp(int userId, String otp, LocalDateTime expiry) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setOtp(otp);
                user.setOtpExpiry(expiry);
                session.update(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Verifies the provided OTP for a specific user, validating its correctness and freshness (not expired).
     *
     * @param id         The ID of the user requesting OTP verification.
     * @param enteredOtp The OTP inputted by the user.
     * @return true if verification succeeds, false otherwise.
     */
    public boolean verifyOtp(int id, String enteredOtp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = session.get(User.class, id);

            if (user != null && user.getOtp() != null && user.getOtpExpiry() != null) {
                boolean isNotExpired = LocalDateTime.now().isBefore(user.getOtpExpiry());
                if (isNotExpired && user.getOtp().equals(enteredOtp)) {
                    // clear OTP after successful verification automatically
                    user.setOtp(null);
                    user.setOtpExpiry(null);
                    session.update(user);
                    tx.commit();
                    return true;
                }
            }

            tx.commit();
            return false;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clears an OTP from the user record upon successful validation or expiration.
     *
     * @param id The ID of the targeted User.
     */
    public void clearOtp(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                user.setOtp(null);
                user.setOtpExpiry(null);
                session.update(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

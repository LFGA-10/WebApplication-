package com.downloader.skillswitch.servlet;

import com.downloader.skillswitch.model.User;
import com.downloader.skillswitch.service.UserService;
import com.downloader.skillswitch.util.EmailUtil;
import com.downloader.skillswitch.util.OtpUtil;
import com.downloader.skillswitch.util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Servlet handling user login requests.
 * Authenticates the user securely and initiates the Two-Factor Authentication (OTP) process.
 * Applies sanitization and checks hashed credentials to ensure secure login interactions.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = SecurityUtil.sanitize(request.getParameter("email"));
        String password = SecurityUtil.hashPassword(request.getParameter("password"));

        User user = userService.login(email, password);

        if (user != null) {
            // Generate OTP
            String otp = OtpUtil.generateOTP();
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

            // Save OTP in DB
            userService.saveOtp(user.getId(), otp, expiry);

            // Send email
            try {
                EmailUtil.sendOTP(user.getEmail(), otp);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Store temporary user in session
            HttpSession session = request.getSession();
            session.setAttribute("tempUser", user);

            // Redirect to OTP verification page
            response.sendRedirect("verifyOtp.jsp");

        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }

    // Optional: redirect GET requests to login form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}

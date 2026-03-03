package com.downloader.skillswitch.servlet;

import com.downloader.skillswitch.model.User;
import com.downloader.skillswitch.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

/**
 * Servlet handling the verification of One-Time Passwords (OTPs) for Two-Factor Authentication.
 */
@WebServlet("/verify-otp")
public class VerifyOtpServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String enteredOtp = request.getParameter("otp");

        HttpSession session = request.getSession();
        User tempUser = (User) session.getAttribute("tempUser");

        if (tempUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        boolean isValid = userService.verifyOtp(tempUser.getId(), enteredOtp);

        if (isValid) {

            // Remove OTP from DB
            userService.clearOtp(tempUser.getId());

            // Remove temp user
            session.removeAttribute("tempUser");

            // Now create REAL login session
            session.setAttribute("user", tempUser);

            response.sendRedirect("dashboard.jsp");

        } else {
            response.sendRedirect("verifyOtp.jsp?error=1");
        }
    }
}

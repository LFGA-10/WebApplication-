package com.downloader.skillswitch.servlet;

import com.downloader.skillswitch.model.User;
import com.downloader.skillswitch.service.UserService;
import com.downloader.skillswitch.util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet handling user registration requests.
 * Registers new users ensuring input is sanitized against XSS attacks,
 * hashes passwords securely, and stores them in the database.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Sanitize input
        String name = SecurityUtil.sanitize(request.getParameter("name"));
        String email = SecurityUtil.sanitize(request.getParameter("email"));
        
        // Hash password securely
        String password = SecurityUtil.hashPassword(request.getParameter("password"));

        User user = new User(name, email, password);

        userService.saveUser(user);

        // Redirect to login page after successful registration
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}

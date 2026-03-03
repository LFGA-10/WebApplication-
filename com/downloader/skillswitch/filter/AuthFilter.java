package com.downloader.skillswitch.filter;

import com.downloader.skillswitch.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter that provides Authentication and Authorization by intercepting all requests to secured resources.
 * Redirects unauthenticated users to the login page and protects admin endpoints.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // Allow access to public pages like login, registration, otp mapping, and static resources
        boolean isPublicResource = uri.endsWith("login") 
                                || uri.endsWith("register") 
                                || uri.endsWith("verify-otp") 
                                || uri.endsWith("login.jsp") 
                                || uri.endsWith("register.jsp") 
                                || uri.endsWith("verifyOtp.jsp") 
                                || uri.contains("/css/") 
                                || uri.contains("/js/") 
                                || uri.contains("/images/");
        
        // Let public resources through
        if (isPublicResource) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is authenticated
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            
            // Authorization Check: enforce access controls based on user roles
            if (uri.contains("/admin") && !"ADMIN".equals(user.getRole())) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized: Access is denied. You require Admin privileges.");
                return;
            }
            chain.doFilter(request, response);
        } else {
            // Unauthenticated user, redirect to login page
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {}
}

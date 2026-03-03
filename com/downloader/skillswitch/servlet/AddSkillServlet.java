package com.downloader.skillswitch.servlet;

import com.downloader.skillswitch.model.Skill;
import com.downloader.skillswitch.model.User;
import com.downloader.skillswitch.model.UserSkill;
import com.downloader.skillswitch.service.UserSkillService;
import com.downloader.skillswitch.service.UserService;
import com.downloader.skillswitch.util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet for handling addition of new skills.
 * Applies XSS sanitization measures to incoming parameters to uphold data security.
 */
@WebServlet("/addSkill")
public class AddSkillServlet extends HttpServlet {

    private UserSkillService userSkillService = new UserSkillService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Sanitize input to prevent injection
        String skillName = SecurityUtil.sanitize(request.getParameter("skillName"));
        String category = SecurityUtil.sanitize(request.getParameter("category"));
        String level = SecurityUtil.sanitize(request.getParameter("level"));

        // Save skill first
        Skill skill = new Skill(skillName, category);
        
        // Using session directly here
        userSkillService.saveUserSkill(new UserSkill(user, skill, level));

        response.sendRedirect("dashboard.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("addSkill.jsp");
    }
}

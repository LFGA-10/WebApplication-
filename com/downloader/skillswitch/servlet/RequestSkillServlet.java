package com.downloader.skillswitch.servlet;

import com.downloader.skillswitch.model.*;
import com.downloader.skillswitch.service.SkillRequestService;
import com.downloader.skillswitch.util.HibernateUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.hibernate.Session;
import java.io.IOException;

@WebServlet("/requestSkill")
public class RequestSkillServlet extends HttpServlet {

    private SkillRequestService service = new SkillRequestService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User requester = (User) request.getSession().getAttribute("user");
        if (requester == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int skillId = Integer.parseInt(request.getParameter("skillId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        Session session = HibernateUtil.getSessionFactory().openSession();
        Skill skill = session.get(Skill.class, skillId);
        User teacher = session.get(User.class, teacherId);
        session.close();

        SkillRequest req = new SkillRequest(requester, teacher, skill, "PENDING");
        service.save(req);

        response.sendRedirect("browseSkills.jsp");
    }
}

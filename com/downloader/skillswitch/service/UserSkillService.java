package com.downloader.skillswitch.service;

import com.downloader.skillswitch.model.UserSkill;
import com.downloader.skillswitch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserSkillService {

    public void saveUserSkill(UserSkill userSkill) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(userSkill);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

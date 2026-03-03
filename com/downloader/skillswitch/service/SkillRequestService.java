package com.downloader.skillswitch.service;

import com.downloader.skillswitch.model.SkillRequest;
import com.downloader.skillswitch.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SkillRequestService {

    public void save(SkillRequest request) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(request);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}

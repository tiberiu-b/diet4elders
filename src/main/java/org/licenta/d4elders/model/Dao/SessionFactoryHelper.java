package org.licenta.d4elders.model.Dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by cristiprg on 01.03.2015.
 */
public class SessionFactoryHelper {
    private static SessionFactoryHelper ourInstance = new SessionFactoryHelper();

    private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static SessionFactoryHelper getInstance() {
        return ourInstance;
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    private SessionFactoryHelper() {

    }
}

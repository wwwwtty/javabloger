package org.hsc.novelSpider.dao.utils;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static final Logger log=LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
        	URL configPath=HibernateUtil.class.getResource("/hibernateConfiguration.xml");
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure(configPath).buildSessionFactory();
        }
        catch (Throwable ex) {
        	log.error("Initial SessionFactory creation failed."+ex.getMessage() , ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
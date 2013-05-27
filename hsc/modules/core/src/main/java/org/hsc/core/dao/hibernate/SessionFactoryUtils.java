package org.hsc.core.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author heshengchao@qq.com 
 * @since  2013-5-27 上午11:10:25
 */
public class SessionFactoryUtils {

	public static SessionFactory sessionFactory;
	
	public static void init(){
		 Configuration cfg = new Configuration();  
	     cfg.configure("hibernate.cfg.xml");//读取配置文件  
	          
	     ServiceRegistry serviceRegistry =new ServiceRegistryBuilder().  
	     applySettings(cfg.getProperties()).buildServiceRegistry();  
	          
	     sessionFactory= cfg.configure().buildSessionFactory(serviceRegistry);  
	}
	
	/**
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		if(sessionFactory==null){
			init();
		}
		return sessionFactory;
	}
}

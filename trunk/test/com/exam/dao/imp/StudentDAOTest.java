package com.exam.dao.imp;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import com.exam.utils.SpringUtils;

/*
 *@auther heshencao 
 * 2009-5-14 上午06:10:15
 */
public class StudentDAOTest extends TestCase{

	private StudentDAOImp studentDao;
	
	public void setUp(){
		ApplicationContext context=SpringUtils.getApplicationContext();
		studentDao=(StudentDAOImp)context.getBean("studentDAO");
	}
	
	public void testFindAll(){
		studentDao.findAll();
	}
}

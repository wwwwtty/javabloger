package com.exam.action;

import org.springframework.context.ApplicationContext;

import com.exam.dao.imp.StudentDAOImp;
import com.exam.utils.SpringUtils;

import junit.framework.TestCase;

/*
 *@auther heshencao 
 * 2009-8-16 上午10:35:47
 */
public class StudentActionTest extends TestCase {

	private StudentAction studentAction;
	
	public void setUp(){
		ApplicationContext context=SpringUtils.getApplicationContext();
		studentAction=(StudentAction)context.getBean("studentAction");
	}
	
	public void testFindAll(){
		studentAction.findAll();
	}
}

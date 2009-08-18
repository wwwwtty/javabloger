package com.exam.service;

import org.springframework.context.ApplicationContext;

import com.exam.dao.imp.StudentDAOImp;
import com.exam.utils.SpringUtils;

import junit.framework.TestCase;

/*
 *@auther heshencao 
 * 2009-8-16 上午10:35:47
 */
public class StudentServiceTest extends TestCase {

	private StudentService studentService;
	
	public void setUp(){
		ApplicationContext context=SpringUtils.getApplicationContext();
		studentService=(StudentService)context.getBean("studentService");
	}
	
	public void testFindAll(){
		System.out.println(studentService.findStudentAll());
	}
}

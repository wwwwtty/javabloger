package com.exam.action;

import com.exam.domain.Student;
import com.exam.service.StudentService;

/*
 *@auther heshencao 
 * 2009-8-15 上午01:42:37
 */
public class StudentAction extends BaseAction {

	private Student student;
	private StudentService studentService;
	
	/**添加学习信息;
	 * @return
	 */
	public String add(){		
		studentService.addStudent(student);
		return getMapping("student");
	}
	
	/**
	 * @return
	 */
	public String delete(){		
		studentService.deleteStudentById(student.getStudentId());
		return getMapping("student");
	}
	
	/**
	 * @return
	 */
	public String update(){		
		studentService.deleteStudentById(student.getStudentId());
		return getMapping("student");
	}
	
	/*
	 * 
	 */
	
	public String findAll(){
		this.setList(studentService.findStudentAll());
		return getMapping("student");
	}

	private String getMapping(String str) {
		this.setTarget(str);
		return this.SUCCESS;
	}
	
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	
}

package com.exam.service;

import java.util.List;

import com.exam.dao.face.StudentDAO;
import com.exam.domain.Student;
import com.exam.utils.HibernatePage;

/**
 * @author YingLian,2009-8-15 上午01:52:30
 *
 */
public class StudentService {

	private StudentDAO studentDao;

	public StudentDAO getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDAO studentDao) {
		this.studentDao = studentDao;
	}
	
	
	
	public void addStudent(Student s){
		this.studentDao.save(s);
	}
	
	public void updateStudent(Student s){
		this.studentDao.update(s);
	}
	
	public void deleteStudentById(int id){
		this.studentDao.deleteByID(id);
	}
	
	public Student findStudentById(String id){
		return this.studentDao.findById(id);
	}
	
	public List<Student> findStudentAll(){
		return this.studentDao.findAll();
	}
	
}

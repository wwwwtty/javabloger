package com.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Exam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam", catalog = "exam")
public class Exam implements java.io.Serializable {

	// Fields
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "exam_id", unique = true, nullable = false)
	private Integer examId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name = "exam_name", length = 100)
	private String examName;
	
	@ManyToMany(mappedBy = "studentExams", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Student> students;
	// Constructors

	/** default constructor */
	public Exam() {
	}

	/** full constructor */
	public Exam(Course course, String examName) {
		this.course = course;
		this.examName = examName;
	}

	// Property accessors
	
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	
	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Student> getStudents() {
		return students;
	}

}
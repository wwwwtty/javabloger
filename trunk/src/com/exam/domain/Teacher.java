package com.exam.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher", catalog = "exam")
public class Teacher implements java.io.Serializable {

	// Fields
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "teacher_id", unique = true, nullable = false)
	private Integer teacherId;
	
	@Column(name = "login_name", length = 20)
	private String loginName;
	
	@Column(name = "password", length = 20)
	private String password;
	
	@Column(name = "real_name", length = 20)
	private String realName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")	
	private Set<Course> courses = new HashSet<Course>(0);
//	private Set<Answer> answers = new HashSet<Answer>(0);

	// Constructors

	/** default constructor */
	public Teacher() {
	}

// Property accessors

	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
//	public Set<Answer> getAnswers() {
//		return this.answers;
//	}
//
//	public void setAnswers(Set<Answer> answers) {
//		this.answers = answers;
//	}

}
package com.exam.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author heshencao,2009-8-14 上午03:07:06
 *
 */
@Entity
@Table(name = "class", catalog = "exam")
public class ClassInfo implements java.io.Serializable {

	// Fields
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "class_id", unique = true, nullable = false)
	private Integer classId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profession_id")
	private Profession profession;
	
	@Column(name = "name", length = 20)
	private String name;
	
	@Column(name = "class_desc", length = 200)
	private String desc;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "cls")
	private Set<Course> courses = new HashSet<Course>(0);
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cls")
	private Set<Student> students = new HashSet<Student>(0);

	// Constructors

	/** default constructor */
	public ClassInfo() {
	}

	// Property accessors
	
	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	
	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
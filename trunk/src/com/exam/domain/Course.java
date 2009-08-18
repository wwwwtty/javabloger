package com.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="course",catalog="exam")

public class Course  implements java.io.Serializable { 
    // Fields    
	@Id @GeneratedValue(strategy=IDENTITY)    
    @Column(name="course_id", unique=true, nullable=false)
     private Integer courseId;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="class_id")
//     private ClassInfo cls;
//	
	@ManyToOne
	 @JoinColumn(name = "teacher_id")
     private Teacher teacher;
	
	 @Column(name="name", length=20)
     private String name;
	 
	 @Column(name="course_desc", length=200)
     private String desc;
	 
	 @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="course")
     private Set<Exam> exams = new HashSet<Exam>(0);

	 @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	 @JoinColumn(name = "class_id")
     private ClassInfo cls ;
		/** default constructor */
	 
	 public Course() {
	 }
    // Property accessors    

    public Integer getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
	
    public Teacher getTeacher() {
        return this.teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public Set<Exam> getExams() {
        return this.exams;
    }
    
    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
    
    public ClassInfo getCls() {
		return cls;
	}

	public void setCls(ClassInfo cls) {
		this.cls = cls;
	}
}
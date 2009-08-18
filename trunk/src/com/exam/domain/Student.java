package com.exam.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Student entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="student",catalog="exam")
public class Student  implements java.io.Serializable {


    // Fields    

	 @Id @GeneratedValue(strategy=IDENTITY)    
	 @Column(name="student_id", unique=true, nullable=false)	
     private Integer studentId;
	 
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="class_id")
     private ClassInfo cls;
	 
	 @Column(name="login_name", length=20)
     private String loginName;
	 
	 @Column(name="password", length=20)
     private String password;
	 
	 @Column(name="real_name", length=50)
     private String realName;
	 
	 @Column(name="student_desc", length=200)
     private String studentDesc;
	 
	 @Column(name="other", length=500)
     private String other;
	 
	 @Column(name="sex")
     private boolean sex;
	 
	 @Temporal(TemporalType.DATE)
	 @Column(name="birthday", length=19)
     private Date birthday;
	 
	 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    //CascadeType参考 这里不建议将CascadeType类型设置为ALL
	 @JoinTable(name = "student_exam", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = { @JoinColumn(name = "exam_id") })
	 private Set<Exam> studentExams = new HashSet<Exam>(0);

     // Constructors

    /** default constructor */
    public Student() {
    }    
    // Property accessors
 
    public Integer getStudentId() {
        return this.studentId;
    }    
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }	
    public ClassInfo getCls() {
        return this.cls;
    }    
    public void setCls(ClassInfo cls) {
        this.cls = cls;
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
    public String getStudentDesc() {
        return this.studentDesc;
    }    
    public void setStudentDesc(String studentDesc) {
        this.studentDesc = studentDesc;
    }
    public String getOther() {
        return this.other;
    }
    
    public void setOther(String other) {
        this.other = other;
    }
    public boolean getSex() {
        return this.sex;
    }
    
    public void setSex(boolean sex) {
        this.sex = sex;
    }   
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Exam> getStudentExams() {
        return this.studentExams;
    }
    
    public void setStudentExams(Set<Exam> studentExams) {
        this.studentExams = studentExams;
    }
//@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="student")
//
//    public Set<Answer> getAnswers() {
//        return this.answers;
//    }
//    
//    public void setAnswers(Set<Answer> answers) {
//        this.answers = answers;
//    }

}
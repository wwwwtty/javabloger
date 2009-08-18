package com.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 *@auther heshencao 
 * 2009-8-14 上午08:58:24
 */
@Entity
@Table(name="answer",catalog="exam")
public class Answer {

	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "answer_Id", unique = true, nullable = false)
	private int answerId;
	
	@Column(name="answer",length=200)
	private String answer;//学生的答案; 
	
	@Column(name="score")
	private float score; //得分
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;//评卷人;
	
	@Column(name="remark",length=200)
	private String remark;//评语.(给分的原因)

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

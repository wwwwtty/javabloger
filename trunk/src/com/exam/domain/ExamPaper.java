package com.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ExamPaper entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam_paper", catalog = "exam")
public class ExamPaper implements java.io.Serializable {

	// Fields
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "paper_id", unique = true, nullable = false)
	private Integer paperId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quest_id")
	private Quest quest;
	
	@Column(name = "exam_id")
	private Integer examId;
	
	@Column(name = "quest_score", precision = 12, scale = 0)
	private Float questScore;

	// Constructors

	/** default constructor */
	public ExamPaper() {
	}


	// Property accessors
	
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	
	public Quest getQuest() {
		return this.quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	
	public Float getQuestScore() {
		return this.questScore;
	}

	public void setQuestScore(Float questScore) {
		this.questScore = questScore;
	}

}
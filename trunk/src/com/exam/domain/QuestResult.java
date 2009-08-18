package com.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 *@auther heshencao 
 * 2009-8-14 上午04:20:10
 */
@Entity
@Table(name = "result", catalog = "exam")
public class QuestResult {

	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "result_id", unique = true, nullable = false)
	private int resultId;;
	
	@Column(name = "result", length = 200)
	private String result;//解答;
	
	@Column(name = "is_right")
	private boolean right;//是否为正确答案;
	
	@Column(name = "ordinal", length = 100)
	private int ordinal;//解答序号;
	
	@ManyToOne
    @JoinColumn(name="quest_id", nullable=false)
	private Quest quest ;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public void setQuest(Quest results) {
		this.quest = results;
	}
	public Quest getQuest() {
		return this.quest;
	}
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	public int getOrdinal() {
		return ordinal;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getResultId() {
		return resultId;
	}
	
	
	
}

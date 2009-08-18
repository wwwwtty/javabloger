package com.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author heshencao,2009-8-14 上午03:48:32
 *
 */
@Entity
@Table(name = "quest", catalog = "exam" )
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="type",discriminatorType = DiscriminatorType.STRING,
                                         length=2)
@DiscriminatorValue("QU")
public abstract class Quest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "quest_id", unique = true, nullable = false)
	private Integer questId;
	
	@Column(name = "title", length = 300)
	private String title;
	
	@Column(name = "kernel", length = 200)
	private String kernel;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="quest")
	private List<QuestResult> questResult;

	
	// Property accessors
	
	public Integer getQuestId() {
		return this.questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getKernel() {
		return this.kernel;
	}

	public void setKernel(String kernel) {
		this.kernel = kernel;
	}
	

	public void setQuestResult(List<QuestResult> questResult) {
		this.questResult = questResult;
	}

	public List<QuestResult> getQuestResult() {
		return questResult;
	}
}
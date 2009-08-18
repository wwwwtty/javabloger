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
 * Profession entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "profession", catalog = "exam")
public class Profession implements java.io.Serializable {

	// Fields

	private Integer professionId;
	private Integer name;
	private String professionDesc;
	private Set<ClassInfo> classes = new HashSet<ClassInfo>(0);

	// Constructors

	/** default constructor */
	public Profession() {
	}

	/** full constructor */
	public Profession(Integer name, String professionDesc, Set<ClassInfo> classes) {
		this.name = name;
		this.professionDesc = professionDesc;
		this.classes = classes;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "profession_id", unique = true, nullable = false)
	public Integer getProfessionId() {
		return this.professionId;
	}

	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}

	@Column(name = "name")
	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	@Column(name = "profession_desc", length = 200)
	public String getProfessionDesc() {
		return this.professionDesc;
	}

	public void setProfessionDesc(String professionDesc) {
		this.professionDesc = professionDesc;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "profession")
	public Set<ClassInfo> getClasses() {
		return this.classes;
	}

	public void setClasses(Set<ClassInfo> classes) {
		this.classes = classes;
	}

}
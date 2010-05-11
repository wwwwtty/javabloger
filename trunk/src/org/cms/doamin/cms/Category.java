package org.cms.doamin.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;


/**
 * CmsCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_category", catalog = "tinycms", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Category extends Object implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "categoryId", unique = true, nullable = false)
	private String id;
	@Column(name = "createUserID")
	private String createUserId;
	@Column(name = "Categoryname", length = 20)
	@NotNull
	private String name;
	@Column(name = "categoryDesc", length = 100)
	private String desc;
	@Column(name = "code", unique = true, length = 20)
	private String code;
	@Column(name = "invalid", length = 1)
	private String enable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Column(name = "grade", length = 20)
	private String grade;


}
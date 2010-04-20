package org.cms.doamin.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AuthRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_role", catalog = "tinycms")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 4907561970333211563L;

	// Fields
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "role_id", unique = true, nullable = false, length = 32)
	private String roleId;
	
	@Column(name = "role_name", nullable = false, length = 30)
	private String roleName;
	@Column(name = "role_desc", length = 100)
	private String roleDesc;
	@Column(name = "role_code", length = 30)
	private String roleCode;
	@Column(name = "grade", length = 20)
	private String grade;
	@Column(name = "enabled")
	private Boolean enabled=true;

	// Constructors
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleCode) {
		this.roleCode = roleCode;
	}

	/** full constructor */
	public Role(String roleName, String roleDesc, String roleCode,Boolean enable) {
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleCode = roleCode;
		this.enabled = enable;
	}

	// Property accessors
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enable) {
		this.enabled = enable;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return grade;
	}

}
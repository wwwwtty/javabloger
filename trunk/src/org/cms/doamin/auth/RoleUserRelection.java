package org.cms.doamin.auth;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AuthRoleUserRelection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_role_user_relection", catalog = "tinycms")
public class RoleUserRelection implements java.io.Serializable {

	// Fields
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = User.class) 
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)  
	private User user;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = Role.class) 
	@JoinColumn(name = "role_id", referencedColumnName = "role_id", unique = true)  
	private Role role;

	@Column(name = "enable")
	private Boolean enable;
	// Constructors

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
package org.cms.doamin.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AuthUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_user", catalog = "tinycms")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 2071283935099579017L;
	// Fields
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id", unique = true, nullable = false, length = 32)
	private String userId;
	@Column(name = "username", length = 20, unique = true)
	private String username;
	@Column(name = "password", length = 20)
	private String password;
	@Column(name = "realName", length = 20)
	private String realName;
	@Column(name = "sex")
	private boolean sex;
	@Column(name = "showName", length = 50)
	private String showName;
	@Column(name = "enabled", length = 1)
	private boolean enabled=true;
	@Column(name = "locked", length = 1)
	private boolean locked=false;
	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String password, String realName,
			boolean sex, String showName) {
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.sex = sex;
		this.showName = showName;
	}

	// Property accessors
		public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	
	public boolean getSex() {
		return this.sex;
	}

	/**性别设置(是否为男性) */
	public void setSex(boolean isboy) {
		this.sex = isboy;
	}

		public String getShowName() {
		return this.showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

}
package org.cms.doamin.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AuthFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth_fun", catalog = "tinycms")
public class Function implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Column(name = "fun_id", unique = true, nullable = false, length = 32)
	private String id;

	@Column(name = "fun_name", length = 50)
	private String name;
	
	@Column(name = "url", length = 100)
	private String url;
	
	@Column(name = "group_code", length = 50)
	private String groupCode;
	
	@Column(name = "fun_code", length = 50)
	private String code;
	
	@Column(name = "enabled", length = 1)
	private String enabled="Y";
	
	@Column(name = "fun_desc", length = 100)
	private String info;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getEnabled() {
		return enabled;
	}
}
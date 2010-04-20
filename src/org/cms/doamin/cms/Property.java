package org.cms.doamin.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CmsProperty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_property", catalog = "tinycms")
public class Property implements java.io.Serializable {

	// Fields

	private String id;
	private String key;
	private String value;
	private String keydesc;
	private String foriegnId;
	private String extend;

	// Constructors

	/** default constructor */
	public Property() {
	}

	/** minimal constructor */
	public Property(String foriegnId, String extend) {
		this.foriegnId = foriegnId;
		this.extend = extend;
	}

	/** full constructor */
	public Property(String key, String value, String keydesc,
			String foriegnId, String extend) {
		this.key = key;
		this.value = value;
		this.keydesc = keydesc;
		this.foriegnId = foriegnId;
		this.extend = extend;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "key", length = 20)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "value", length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "keydesc", length = 100)
	public String getKeydesc() {
		return this.keydesc;
	}

	public void setKeydesc(String keydesc) {
		this.keydesc = keydesc;
	}

	@Column(name = "foriegnID", nullable = false, length = 32)
	public String getForiegnId() {
		return this.foriegnId;
	}

	public void setForiegnId(String foriegnId) {
		this.foriegnId = foriegnId;
	}

	@Column(name = "extend", nullable = false, length = 40)
	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}
package org.cms.doamin.site;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * SitePage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "site_page", catalog = "tinycms")
public class SitePage implements java.io.Serializable {

	// Fields

	private String pageId;
	private String siteId;
	private String name;
	private String namespace;
	private String type;
	private String code;

	// Constructors

	/** default constructor */
	public SitePage() {
	}

	/** minimal constructor */
	public SitePage(String siteId) {
		this.siteId = siteId;
	}

	/** full constructor */
	public SitePage(String siteId, String name, String namespace, String type,
			String code) {
		this.siteId = siteId;
		this.name = name;
		this.namespace = namespace;
		this.type = type;
		this.code = code;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "pageId", unique = true, nullable = false, length = 32)
	public String getPageId() {
		return this.pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Column(name = "siteId", nullable = false, length = 32)
	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "namespace", length = 50)
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Column(name = "type", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "code", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
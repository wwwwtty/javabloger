package org.cms.doamin.site;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * SiteWebsite entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "site_website", catalog = "tinycms")
public class SiteWebsite implements java.io.Serializable {

	// Fields

	private String siteId;
	private String siteName;
	private String siteDesc;
	private String code;
	private String enable;

	// Constructors

	/** default constructor */
	public SiteWebsite() {
	}

	/** full constructor */
	public SiteWebsite(String siteName, String siteDesc, String code,
			String enable) {
		this.siteName = siteName;
		this.siteDesc = siteDesc;
		this.code = code;
		this.enable = enable;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "siteId", unique = true, nullable = false, length = 32)
	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Column(name = "siteName", length = 20)
	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@Column(name = "siteDesc", length = 100)
	public String getSiteDesc() {
		return this.siteDesc;
	}

	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}

	@Column(name = "code", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "enable", length = 32)
	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}
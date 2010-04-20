package org.cms.doamin.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CmsNewsContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_news_content", catalog = "tinycms")
public class NewsRefContent implements java.io.Serializable {

	// Fields

	private String contentId;
	private String newsId;
	private String pageIndex;
	private String content;

	// Constructors

	/** default constructor */
	public NewsRefContent() {
	}

	/** full constructor */
	public NewsRefContent(String newsId, String pageIndex, String content) {
		this.newsId = newsId;
		this.pageIndex = pageIndex;
		this.content = content;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "contentId", unique = true, nullable = false, length = 32)
	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Column(name = "newsID", length = 32)
	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	@Column(name = "page_Index", length = 32)
	public String getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
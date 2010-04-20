package org.cms.doamin.cms;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CmsNews entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_news", catalog = "tinycms")
public class News implements java.io.Serializable {

	// Fields

	private String newsId;
	private String createUserId;
	private String title;
	private String subTitle;
	private Timestamp createTime;
	private Timestamp publishTime;
	private String isDrag;
	private String source;
	private String publishAuthor;

	// Constructors

	/** default constructor */
	public News() {
	}

	/** full constructor */
	public News(String createUserId, String title, String subTitle,
			Timestamp createTime, Timestamp publishTime, String isDrag,
			String source, String publishAuthor) {
		this.createUserId = createUserId;
		this.title = title;
		this.subTitle = subTitle;
		this.createTime = createTime;
		this.publishTime = publishTime;
		this.isDrag = isDrag;
		this.source = source;
		this.publishAuthor = publishAuthor;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "newsID", unique = true, nullable = false, length = 32)
	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	@Column(name = "createUserID", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "subTitle", length = 300)
	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "publishTime", length = 19)
	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "isDrag", length = 1)
	public String getIsDrag() {
		return this.isDrag;
	}

	public void setIsDrag(String isDrag) {
		this.isDrag = isDrag;
	}

	@Column(name = "source", length = 50)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "publishAuthor", length = 50)
	public String getPublishAuthor() {
		return this.publishAuthor;
	}

	public void setPublishAuthor(String publishAuthor) {
		this.publishAuthor = publishAuthor;
	}

}
package org.cms.doamin.cms;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CmsComment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_comment", catalog = "tinycms")
public class Comment implements java.io.Serializable {

	// Fields

	private String commentId;
	private String newsId;
	private String createUserId;
	private String title;
	private String comment;
	private Timestamp createTime;
	private String showName;
	private String enable;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** full constructor */
	public Comment(String newsId, String createUserId, String title,
			String comment, Timestamp createTime, String showName, String enable) {
		this.newsId = newsId;
		this.createUserId = createUserId;
		this.title = title;
		this.comment = comment;
		this.createTime = createTime;
		this.showName = showName;
		this.enable = enable;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "comment_id", unique = true, nullable = false, length = 32)
	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	@Column(name = "newsID", length = 32)
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

	@Column(name = "comment", length = 65535)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "showName", length = 20)
	public String getShowName() {
		return this.showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	@Column(name = "enable", length = 1)
	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}
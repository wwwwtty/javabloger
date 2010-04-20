package org.cms.doamin.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CmsCategoryNews entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cms_category_news", catalog = "tinycms")
public class CategoryRefNews implements java.io.Serializable {

	// Fields
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	@Column(name = "categoryId", length = 32)
	private String categoryId;
	@Column(name = "newsID", length = 32)
	private String newsId;

	// Constructors

	/** default constructor */
	public CategoryRefNews() {
	}

	/** full constructor */
	public CategoryRefNews(String categoryId, String newsId) {
		this.categoryId = categoryId;
		this.newsId = newsId;
	}

	// Property accessors
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	
	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

}
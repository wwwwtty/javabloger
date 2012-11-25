package org.hsc.novelSpider.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "novel_chapter")
public class ArticleChapter {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ch_id", unique = true, nullable = false)
	private Integer ID;
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name = "art_id",insertable=false,updatable=false)
	private Article article;
	@Column(name = "art_id")
	private Integer artID;
	@Column(name = "ch_url", unique = true, nullable = false)
	private String url;
	@Column(name = "reference", unique = true, nullable = false)
	private String reference;
	@Column(name = "index", unique = true, nullable = false)
	private String index;
	@Column(name = "create_time", unique = true, nullable = false)
	private Date createTime;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getArtID() {
		return artID;
	}
	public void setArtID(Integer artID) {
		this.artID = artID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
}

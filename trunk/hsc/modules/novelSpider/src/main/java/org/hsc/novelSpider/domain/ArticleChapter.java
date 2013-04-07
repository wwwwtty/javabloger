package org.hsc.novelSpider.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "novel_chapter")
public class ArticleChapter {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ch_id")
	private Integer ID;
//	@ManyToOne(fetch=FetchType.LAZY)
//	@Column(name = "art_id",insertable=false,updatable=false)
//	private Article article;
	@Column(name = "art_id")
	private Integer artID;
	
	@Column(name = "section")
	private String section;
	@Column(name = "ch_url")
	private String url;
	@Column(name = "content" ,columnDefinition="text")
	private String content;
	@Column(name = "title",length=50)
	private String title;
	@Column(name = "reference")
	private String reference;
	@Column(name = "seq_no")
	private int index;
	@Column(name = "create_time")
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
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
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
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

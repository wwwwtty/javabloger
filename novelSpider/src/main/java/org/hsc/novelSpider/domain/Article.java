package org.hsc.novelSpider.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "novel_article")
public class Article {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "art_id", unique = true, nullable = false)
	private Integer ID;
	@Column(name = "art_name", unique = true, nullable = false)
	private String name;
	@Column(name = "art_desc", unique = true, nullable = false)
	private String desc;
	@Column(name = "art_author", unique = true, nullable = false)
	private String author;
	@Column(name = "create_time", unique = true, nullable = false)
	private Date createTime;
	
	@OneToMany(fetch=FetchType.LAZY)
	@Column(name = "ch_id",insertable=false,updatable=false)
	private List<ArticleChapter> chpters=new ArrayList<ArticleChapter>();
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<ArticleChapter> getChpters() {
		return chpters;
	}
	public void setChpters(List<ArticleChapter> chpters) {
		this.chpters = chpters;
	}
}

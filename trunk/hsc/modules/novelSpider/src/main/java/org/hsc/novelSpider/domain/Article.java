package org.hsc.novelSpider.domain;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "novel_article")
public class Article {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "art_id")
	private Integer ID;
	@Column(name = "art_name")
	private String name;
	@Column(name = "art_desc",columnDefinition="text")
	private String desc;
	@Column(name = "art_author")
	private String author;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "last_update_time")
	private Date lastUpdate;
	/**推荐数*/
	@Column(name = "recommend_point_totle")
	private int recommendPointTotle;
	@Column(name = "recommend_point_week")
	private int recommendPointWeek;
	@Column(name = "recommend_point_month")
	private int recommendPointMonth;
	
	/**点击数*/
	@Column(name = "click_totle")
	private int clickTotle;
	@Column(name = "click_month")
	private int clickmonth;
	@Column(name = "click_week")
	private int clickWeek;
	@Transient
	private String charpterPageUrl;
	
	/**收 藏 数*/
	@Column(name = "collected")
	private int collected;
	
//	@OneToMany(fetch=FetchType.LAZY)
//	private List<ArticleChapter> chpters=new ArrayList<ArticleChapter>();
	
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
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastupdate) {
		this.lastUpdate = lastupdate;
	}
	public int getRecommendPointTotle() {
		return recommendPointTotle;
	}
	public void setRecommendPointTotle(int recommendPointTotle) {
		this.recommendPointTotle = recommendPointTotle;
	}
	public int getRecommendPointWeek() {
		return recommendPointWeek;
	}
	public void setRecommendPointWeek(int recommendPointWeek) {
		this.recommendPointWeek = recommendPointWeek;
	}
	public int getRecommendPointMonth() {
		return recommendPointMonth;
	}
	public void setRecommendPointMonth(int recommendPointMonth) {
		this.recommendPointMonth = recommendPointMonth;
	}
	public int getCollected() {
		return collected;
	}
	public void setCollected(int collected) {
		this.collected = collected;
	}
	public int getClickTotle() {
		return clickTotle;
	}
	public void setClickTotle(int clickTotle) {
		this.clickTotle = clickTotle;
	}
	public int getClickmonth() {
		return clickmonth;
	}
	public void setClickmonth(int clickmonth) {
		this.clickmonth = clickmonth;
	}
	public int getClickWeek() {
		return clickWeek;
	}
	public void setClickWeek(int clickWeek) {
		this.clickWeek = clickWeek;
	}
	
	public String getCharpterPageUrl() {
		return charpterPageUrl;
	}
	public void setCharpterPageUrl(String charpterPageUrl) {
		this.charpterPageUrl = charpterPageUrl;
	}
}

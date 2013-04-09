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
	@Column(name = "title",length=200)
	private String title;
	@Column(name = "reference")
	private String reference;
	/**更新时间（来源）*/
	@Column(name = "ref_update_time")
	private Date refUpdateTime;
	@Column(name = "seq_no")
	private int index;
	@Column(name = "create_time")
	private Date createTime;
	
	/**数据抓取状态*/
	@Column(name = "parse_status")
	private int parseStatus;
	
	public int getParseStatus() {
		return parseStatus;
	}
	public void setParseStatus(int parseStatus) {
		this.parseStatus = parseStatus;
	}
	public Integer getID() {
		return this.ID;
	}
	public void setID(Integer iD) {
		this.ID = iD;
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
	public Date getRefUpdateTime() {
		return refUpdateTime;
	}
	public void setRefUpdateTime(Date refUpdateTime) {
		this.refUpdateTime = refUpdateTime;
	}
	
	/**获取状态*/
	public enum ParseStatus{
		NOT_RUN(0,"未开始"),RUN(1,"正在运行"),FAIL(2,"失败");
		
	    private  String name;  
	    private   int  index; 
		private ParseStatus(int index,String value){
			this.index=index;this.name=value;
		}
		
		public   static  String getName( int  index) {  
	        for  (ParseStatus c : ParseStatus.values()) {  
	            if  (c.getIndex() == index) {  
	                return  c.name;  
	            }  
	        }  
	        return   null ;  
	    }
		
	    public  String getName() {  
	        return  name;  
	    }
	    public   int  getIndex() {  
	        return  index;  
	    }
	}
}

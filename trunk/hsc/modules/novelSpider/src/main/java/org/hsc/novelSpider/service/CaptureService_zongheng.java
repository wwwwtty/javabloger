package org.hsc.novelSpider.service;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.io.NetUtil;
import jodd.lagarto.dom.jerry.Jerry;
import jodd.lagarto.dom.jerry.JerryFunction;

import org.apache.commons.lang3.StringUtils;
import org.hsc.core.utils.DateFormatUtils;
import org.hsc.novelSpider.dao.ArticleChapterDAO;
import org.hsc.novelSpider.dao.ArticleDAO;
import org.hsc.novelSpider.domain.Article;
import org.hsc.novelSpider.domain.ArticleChapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaptureService_zongheng implements ICaptureService {
	private static final Logger log=LoggerFactory.getLogger(CaptureService_17shu.class);
	private @Autowired ArticleDAO articleDao;
	private @Autowired ArticleChapterDAO chpterDao;
	
	
	public void  doCaptureData() throws IOException{
		
		Article art=getArticle("http://book.zongheng.com/book/175703.html");
		Integer articleID=articleDao.save(art);
		getChapter(art.getCharpterPageUrl(),articleID);
	}

	/**获取章节
	 * @param url
	 * @throws IOException
	 */
	public void getChapter(final String url,final Integer articleID) throws IOException {
		
		byte[] str=NetUtil.downloadBytes(url);
        
        Jerry doc = Jerry.jerry(new String(str));
        
        final Jerry sectionEL=doc.$(".chaplist .chapter h2");
        
        final SeqIndex seq=new SeqIndex();
        
        doc.$(".chaplist .chapter .booklist").each(new JerryFunction() {
            public boolean onNode(Jerry $this, int index) {
            	 
            	final String section=sectionEL.get(index).getTextContent();
            	log.info("章节："+section);
            	  
            	$this.$("td").each(new JerryFunction() {
            		  public boolean onNode(Jerry $this, int index) {
            			  
            			  String title=trimBlank($this.text());
            			  String url=$this.$("a").attr("href");
            			  log.info("title:"+title+",url:"+url);
                          
                          ArticleChapter ch= getCharpterContent(url);
                         
                          ch.setCreateTime(new Date());
                          ch.setArtID(articleID);
                          ch.setIndex(seq.seq++);
                          ch.setSection(section);
                          ch.setTitle(title);
                          ch.setUrl(url);
                         
                          chpterDao.save(ch);
                          
                          return true;
            		  }
            	 });
            	 
            	 return true;
            }
        });
	}
	
	
	private ArticleChapter getCharpterContent(String url) {
		 ArticleChapter ch=new ArticleChapter();
		try{
			Jerry doc = Jerry.jerry(new String(NetUtil.downloadBytes(url)));
			Jerry CEL=doc.$("#chapterContent");
			CEL.$("p span").remove();
			CEL.$("div").remove();
			
			ch.setContent(trimBlank(CEL.html()));
			ch.setParseStatus(ArticleChapter.ParseStatus.RUN.getIndex());
			
			String dateText=trimBlank(doc.$(".readcon .tc .number").text());
			
			Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
			Matcher matcher= pattern.matcher(dateText);
			 
			 if(matcher.find()){
				 ch.setRefUpdateTime(DateFormatUtils.parse(matcher.group(0),"yyyy-MM-dd HH:mm:ss"));
			 }
		}catch(Exception e){
			log.error(e.getMessage(),e);
			ch.setParseStatus(ArticleChapter.ParseStatus.FAIL.getIndex());
		}
		
		return ch;
	}
	
	/**获取文章
	 * @param url
	 */
	public Article getArticle(String url){
		 try {
			byte[] html=NetUtil.downloadBytes(url);
			
			 Jerry doc = Jerry.jerry(new String(html));
			 doc.$("table th").each(new JerryFunction() {
		            public boolean onNode(Jerry $this, int index) {
		                log.info($this.find("a").attr("href")+",text:"+$this.text());
		                return true;
		            }
		        });
			
			 Article art=new Article();
			 ///书名 
			 log.info("书名:"+doc.$(".zhbook_info .status h1").text()+",text:"+doc.$(".zhbook_info .status h1").text().trim());
			 String name[]=replaceBlank(doc.$(".zhbook_info .status h1").text()).split(" ");
		     art.setName(name[0]);
		   ///作者 
		     String author=doc.$(".zhbook_info .status p.author a").text();
			 log.info("作者 :"+author);
		     art.setAuthor(trimBlank(StringUtils.trim(author)));
		   ///收 藏 数
		  //   log.info("收 藏 数:"+doc.$("table th:eq(3)").text()+",text:"+doc.$("table td:eq(3)").text().trim());
		   //  art.setCollected(parseInt(doc.$("table td:eq(3)").text()));
		     ///最后更新
		     String lastUpdate=doc.$(".zhbook_info .status p:last").text();
		     lastUpdate=lastUpdate.split("：")[1];
		     log.info("最后更新:"+lastUpdate);
		     art.setLastUpdate(DateFormatUtils.parse(StringUtils.trim(lastUpdate),"yyyy-MM-dd"));
		     ///总点击数
		     String clickTotle=doc.$(".zhbook_info .status p:eq(4)").text();
		     clickTotle=clickTotle.split("：")[1];
		     log.info("总点击数:"+clickTotle);
		     art.setClickTotle(parseInt(clickTotle));
		     
		     ///总推荐数
//		     log.info(doc.$("table th:eq(7)").text()+",text:"+doc.$("table td:eq(7)").text());
//		     art.setClickmonth(parseInt(doc.$("table td:eq(7)").text()));
//		     ///总推荐数
//		     log.info(doc.$("table th:eq(8)").text()+",text:"+doc.$("table td:eq(8)").text());
//		     art.setClickWeek(parseInt(doc.$("table td:eq(8)").text()));
//		     ///总推荐数
//		     log.info(doc.$("table th:eq(9)").text()+",text:"+doc.$("table td:eq(9)").text());
//		     art.setRecommendPointTotle(parseInt(doc.$("table td:eq(9)").text()));
//		     ///月推荐数
//		     log.info(doc.$("table th:eq(10)").text()+",text:"+doc.$("table td:eq(10)").text());
//		     art.setRecommendPointMonth(parseInt(doc.$("table td:eq(10)").text()));
//		     ///周推荐数
//		     log.info(doc.$("table th:eq(11)").text()+",text:"+doc.$("table td:eq(11)").text());
//		     art.setRecommendPointWeek(parseInt(doc.$("table td:eq(11)").text()));

//		     int articleID=articleDao.save(art);
		     art.setCharpterPageUrl(doc.$(".zhbook_info .read").attr("href"));
		     
		     return art;
		    
		 } catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		 return null;
	}
	
	private String replaceBlank(String htmlText){
		//return htmlText.replace("&nbsp;","");
		return htmlText.replace("\u00a0"," ");
	}
	private String trimBlank(String htmlText){
		if(StringUtils.isEmpty(htmlText))return "";
		String text=htmlText.replace("&nbsp;","");
		return text.replace("\u00a0","");
	}
	
	private int parseInt(String htmlText){
		String t=trimBlank(htmlText);
		return Integer.parseInt(t);
	}
	
	class SeqIndex{
		public int seq=0;
	}
}

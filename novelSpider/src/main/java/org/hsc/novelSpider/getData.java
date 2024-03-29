package org.hsc.novelSpider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.hsc.novelSpider.dao.ArticleChapterDAO;
import org.hsc.novelSpider.dao.ArticleDAO;
import org.hsc.novelSpider.dao.utils.DateFormat;
import org.hsc.novelSpider.domain.Article;
import org.hsc.novelSpider.domain.ArticleChapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import jodd.io.NetUtil;
import jodd.lagarto.dom.jerry.Jerry;
import jodd.lagarto.dom.jerry.JerryFunction;

/**17shu抓取
 * @author heshengchao
 *
 */
public class getData {

	private static Logger log=LoggerFactory.getLogger(getData.class);
	private ArticleDAO articleDao;
	private ArticleChapterDAO chpterDao;
	
	getData(){
		System.setProperty("spring.profiles.active", "development");
		
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		
		articleDao = ctx.getBean(ArticleDAO.class);
		chpterDao=ctx.getBean(ArticleChapterDAO.class);
	}
	
	public static void  main(String args[]) throws URISyntaxException, IOException{
		new getData().getArticle("http://www.17shu.com/book/24941.html");

	}

	/**获取章节
	 * @param url
	 * @throws IOException
	 */
	private void getChapter(final String url,final Integer articleID) throws IOException {
	//	byte[] str=NetUtil.downloadBytes("http://www.17shu.com/Html/24/24941/");
		
		byte[] str=NetUtil.downloadBytes(url);
        String html=new String(str,"gbk");
        
        Jerry doc = Jerry.jerry(html);
        doc.$("table td.L").each(new JerryFunction() {
            public boolean onNode(Jerry $this, int index) {
               
                String content=trimBlank(getCharpterContent(url+$this.find("a").attr("href")));
                String title=$this.text();
                
                content=content.replaceAll("<b>一起看书网,看书无弹窗,牢记网址:www.17shu.com</b>","");
                log.info(title+",text:"+content);
                
                ArticleChapter ch=new ArticleChapter();
                ch.setCreateTime(new Date());
                ch.setArtID(articleID);
                ch.setIndex(index);
                ch.setContent(content);
                ch.setTitle(title);
                
                chpterDao.save(ch);
                
                return true;
            }
        });
	}
	private String getCharpterContent(String url) {
		try{
		byte[] str=NetUtil.downloadBytes(url);
        	String html=new String(str,"gbk");
        
        	Jerry doc = Jerry.jerry(html);
        	return doc.$("#a_main #contents").html();
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return "";
		}
		
	}
	
	/**获取文章
	 * @param url
	 */
	public void getArticle(String url){
		 try {
			byte[] str=NetUtil.downloadBytes(url);
			String html=new String(str,"gbk");
			
			 Jerry doc = Jerry.jerry(html);
			 doc.$("table th").each(new JerryFunction() {
		            public boolean onNode(Jerry $this, int index) {
		                log.info($this.find("a").attr("href")+",text:"+$this.text());
		                return true;
		            }
		        });
			
			 Article art=new Article();
			 ///书名 
			 log.info(doc.$("h1").text()+",text:"+doc.$("h1").text().trim());
			 String name[]=replaceBlank(doc.$("h1").text()).split(" ");
		     art.setName(name[0]);
		   ///作者 
			 log.info(doc.$("table th:eq(1)").text()+",text:"+doc.$("table td:eq(1)").text().trim());
		     art.setAuthor(trimBlank(doc.$("table td:eq(1)").text().trim()));
		   ///收 藏 数
		     log.info(doc.$("table th:eq(3)").text()+",text:"+doc.$("table td:eq(3)").text().trim());
		     art.setCollected(parseInt(doc.$("table td:eq(3)").text()));
		     ///最后更新
		     log.info(doc.$("table th:eq(5)").text()+",text:"+doc.$("table td:eq(5)").text());
		     art.setLastupdate(parseDate(doc.$("table td:eq(5)").text().trim()));
		     ///总推荐数
		     log.info(doc.$("table th:eq(6)").text()+",text:"+doc.$("table td:eq(6)").text());
		     art.setClickTotle(parseInt(doc.$("table td:eq(6)").text()));
		     ///总推荐数
		     log.info(doc.$("table th:eq(7)").text()+",text:"+doc.$("table td:eq(7)").text());
		     art.setClickmonth(parseInt(doc.$("table td:eq(7)").text()));
		     ///总推荐数
		     log.info(doc.$("table th:eq(8)").text()+",text:"+doc.$("table td:eq(8)").text());
		     art.setClickWeek(parseInt(doc.$("table td:eq(8)").text()));
		     ///总推荐数
		     log.info(doc.$("table th:eq(9)").text()+",text:"+doc.$("table td:eq(9)").text());
		     art.setRecommendPointTotle(parseInt(doc.$("table td:eq(9)").text()));
		     ///月推荐数
		     log.info(doc.$("table th:eq(10)").text()+",text:"+doc.$("table td:eq(10)").text());
		     art.setRecommendPointMonth(parseInt(doc.$("table td:eq(10)").text()));
		     ///周推荐数
		     log.info(doc.$("table th:eq(11)").text()+",text:"+doc.$("table td:eq(11)").text());
		     art.setRecommendPointWeek(parseInt(doc.$("table td:eq(11)").text()));

		     int articleID=articleDao.save(art);
		     
		     String charptersUrl=doc.$(".btnlinks .read").attr("href");
		     getChapter(charptersUrl,articleID);
		 } catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	private Date parseDate(String htmlText){
		String t=trimBlank(htmlText);
		return DateFormat.parse(t,DateFormat.DATE_FORMAT);
	}
}

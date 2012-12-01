package org.hsc.novelSpider;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.hsc.novelSpider.dao.ArticleDAO;
import org.hsc.novelSpider.dao.utils.DateFormat;
import org.hsc.novelSpider.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.lagarto.dom.jerry.Jerry;
import jodd.lagarto.dom.jerry.JerryFunction;
import jodd.util.SystemUtil;

public class getData {

	private static Logger log=LoggerFactory.getLogger(getData.class);
	private ArticleDAO articleDao=new ArticleDAO();
	
	public static void  main(String args[]) throws URISyntaxException, IOException{

       
		new getData().getArticle("http://www.17shu.com/book/24941.html");
		
     //   getChapter();

	}

	private static void getChapter() throws IOException,
			UnsupportedEncodingException {
		byte[] str=NetUtil.downloadBytes("http://www.17shu.com/Html/24/24941/");
        String html=new String(str,"gbk");
        log.info(html);
        
        Jerry doc = Jerry.jerry(html);
        doc.$("table td.L").each(new JerryFunction() {
            public boolean onNode(Jerry $this, int index) {
                log.info($this.find("a").attr("href")+",text:"+$this.text());
                return true;
            }
        });
        
	}
	
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
		     art.setAuthor(doc.$("table td:eq(1)").text().trim());
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

		     articleDao.save(art);
		     
		 } catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private String replaceBlank(String htmlText){
		//return htmlText.replace("&nbsp;","");
		return htmlText.replace("\u00a0"," ");
	}
	private String trimBlank(String htmlText){
		//return htmlText.replace("&nbsp;","");
		return htmlText.replace("\u00a0","");
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

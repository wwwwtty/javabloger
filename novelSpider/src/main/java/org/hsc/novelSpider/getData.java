package org.hsc.novelSpider;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.io.FileUtil;
import jodd.io.NetUtil;
import jodd.lagarto.dom.jerry.Jerry;
import jodd.lagarto.dom.jerry.JerryFunction;
import jodd.util.SystemUtil;

public class getData {

	private static Logger log=LoggerFactory.getLogger(getData.class);
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

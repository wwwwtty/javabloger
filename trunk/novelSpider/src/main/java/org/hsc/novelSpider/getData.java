package org.hsc.novelSpider;

import java.io.File;
import java.io.IOException;
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
//		HttpClient httpclient = new DefaultHttpClient();
//		
//		HttpGet httpget = new HttpGet("http://www.17shu.com/book/24941.html");
//
//        System.out.println("executing request " + httpget.getURI());
//
//        // Create a response handler
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        String responseBody = httpclient.execute(httpget, responseHandler);
//        System.out.println("----------------------------------------");
//        System.out.println(new String(responseBody.getBytes("ISO-8859-1"),"gbk"));
//        System.out.println("----------------------------------------");
//        
        
        // download the page super-efficiently
        
        byte[] str=NetUtil.downloadBytes("http://www.17shu.com/Html/24/24941/");
        String html=new String(str,"gbk");
        log.info(html);
        
//        // create Jerry, i.e. document context
        Jerry doc = Jerry.jerry(html);
// 
//        // parse
        doc.$("table td.L").each(new JerryFunction() {
            public boolean onNode(Jerry $this, int index) {
                log.info($this.find("a").attr("href")+",text:"+$this.text());
                return true;
            }
        });

	}
}

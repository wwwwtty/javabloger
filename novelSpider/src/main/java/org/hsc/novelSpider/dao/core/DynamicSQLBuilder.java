package org.hsc.novelSpider.dao.core;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.InputSource;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class DynamicSQLBuilder implements ResourceLoaderAware,InitializingBean  {  
    private static final Logger logger = LoggerFactory.getLogger(DynamicSQLBuilder.class);  
   
    private String[] fileNames = new String[0];  
    private Map<URL,Long> cachedFile= new HashMap<URL,Long> ();  
    
    private ResourceLoader resourceLoader;  
    private Configuration configuration;
    /** 查询语句名称缓存，不允许重复 */
    protected final static Map<String,Template> templateCache=new HashMap<String,Template>();  
    
    
    public DynamicSQLBuilder(){
    	configuration = new Configuration(); 
    	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
    }
    
    public void setFileNames(String[] fileNames) {  
        this.fileNames = fileNames;  
    }  
  
     
    public String getSQL(String name,Map<String,?> params){
    	
    	Set<Entry<URL, Long>> fileSet=cachedFile.entrySet();
    	for(Entry<URL, Long> et : fileSet){
    		File file=new File(et.getKey().getFile());
    		if(file.lastModified()!=et.getValue()){
    			try {
					this.buildMap(et.getKey());
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
    		}
    	}
    	
    	StringWriter bf=new StringWriter();
        
        Template temp=templateCache.get(name);
			
		try {
			temp.process(params, bf);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	return bf.getBuffer().toString();
    }
  
  
    public void setResourceLoader(ResourceLoader resourceLoader) {  
        this.resourceLoader = resourceLoader;  
    }  
  
    private void buildMap(Resource[] resources) throws IOException {  
        if (resources == null) {  
            return;  
        }  
        for (Resource resource : resources) {  
            buildMap(resource.getURL());  
        }  
    }  
  
    private void buildMap(final URL resource) throws IOException {  
    	
    	
    	Long modifyDate=new File(resource.getFile()).lastModified();
    	cachedFile.put(resource,modifyDate);
    	 
        InputSource inputSource = null;  
        try {  
            
            SAXReader reader=new SAXReader();
            logger.info("加载SQL文件："+resource);
            Document document =reader.read(resource);
            
            document.accept(new VisitorSupport() {
				
				@Override
				public void visit(Element el) {
					final String elementName = el.getName();  
					 try {
						 if ("sql-query".equals(elementName)) {  
								putStatementToCacheMap(el);
						 } else if ("hql-query".equals(elementName)) {  
	                        putStatementToCacheMap(el);  
						 }  
					 } catch (IOException e) {
						 logger.error(e.getMessage(),e);
					} 
				}
				
				 private void putStatementToCacheMap(final Element element)  throws IOException {  
					 
					 String sqlQueryName = element.attribute("name").getText();  
					 Validate.notEmpty(sqlQueryName);  
					 if (templateCache.containsKey(sqlQueryName)) {  
						 logger.warn("重复的sql-query/hql-query语句定义在文件:" + resource + "中，必须保证name的唯一.");  
					 }  
				        
					 Template temp = new Template(sqlQueryName,new StringReader(element.getText()),configuration);
					 templateCache.put(sqlQueryName, temp);
				} 
			});
            
        } catch (Exception e) {  
            logger.error(e.toString());  
            throw new RuntimeException(e);  
        } finally {  
            if (inputSource != null && inputSource.getByteStream() != null) {  
                try {  
                    inputSource.getByteStream().close();  
                } catch (IOException e) {  
                    logger.error(e.toString());  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
  
    }  
  
    

	@Override
	public void afterPropertiesSet() throws Exception {
		
        for (String file : fileNames) {  
            if (this.resourceLoader instanceof ResourcePatternResolver) {  
                Resource[] resources = ((ResourcePatternResolver) this.resourceLoader).getResources(file);  
                buildMap(resources);  
            } else {  
        	
                Resource resource = resourceLoader.getResource(file);  
                buildMap(resource.getURL());  
            }  
        }  
	}  
}  
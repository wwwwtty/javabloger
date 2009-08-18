package com.exam.utils;
import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringUtils {

	public static ApplicationContext getApplicationContext(){
		//当在WEB应用程序中时从Servlet context中获取IOC容器
		ApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		//当在WEB应用示启动时，由FileSystemXmlApplicationContext生成IOC容器
		if(context==null){			
			String path="applicationContext.xml";		
			context=new ClassPathXmlApplicationContext(path);
		}
		return context;
	}
}

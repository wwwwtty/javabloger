package com.exam.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;


/*
 *@auther heshencao 
 * 2009-8-15 上午02:15:56
 */
public class AjaxFilter implements Filter {

	private ApplicationContext application;
	
	public void init(FilterConfig arg0) throws ServletException {
			
	}
	
	public void destroy() {
		application=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(application==null){
			application=SpringUtils.getApplicationContext();	
		}
		
		
		
	}

	

}

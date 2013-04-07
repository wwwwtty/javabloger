package org.hsc.core.web;

import java.util.HashMap;
import java.util.Map;

public class RequestForwordWapper {
	private Map<String,Object> attributes=new HashMap<String,Object>();
	private String forword;

	public String getForword() {
		return forword;
	}

	public void setForword(String forword) {
		this.forword = forword;
	}
	
	public static RequestForwordWapper forword(String url){
		RequestForwordWapper dp=new RequestForwordWapper();
		dp.forword=url;
		return dp;
	}

	public Map<String,Object> getAttributes() {
		return attributes;
	}
	public  Object getAttribute(String key) {
		return attributes.get(key);
	}
	public void setAttribute(String key,Object value) {
		attributes.put(key, value);
	}
	
}

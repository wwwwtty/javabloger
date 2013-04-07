package org.hsc.core.web;

public class RequestRedirectWapper {
	
	private String dispacherURL;

	public String getDispacherURL() {
		return dispacherURL;
	}

	public void setDispacherURL(String dispacherURL) {
		this.dispacherURL = dispacherURL;
	}
	
	public static RequestRedirectWapper dispacher(String url){
		RequestRedirectWapper dp=new RequestRedirectWapper();
		dp.dispacherURL=url;
		return dp;
		
	}
	
}

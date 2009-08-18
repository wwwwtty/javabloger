package com.exam.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.exam.utils.HibernatePage;
import com.opensymphony.xwork2.ActionContext;


public class PageNavigation extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6157045450248274103L;
	private int showNum;
	private HibernatePage pageObject;
	private String url;

	@Override
	public int doEndTag() throws JspTagException {
		if(pageObject==null){
			return this.SKIP_PAGE;
		}
		JspWriter out = this.pageContext.getOut();
		try {
//			String actionUrl = convertHtml(url);
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			String actionUrl=request.getRequestURL().toString();
			//首页&未页	
			out.println("[<a href='" + actionUrl + "?selectPageNum=1'>first</a>");
			out.println("/");
			out.println("<a href='" + actionUrl + "?selectPageNum="+pageObject.getTotlePage()+"'>last</a>]");
			
			int startNum;
			int endNum;
			//循环打印当前指定页面的前后多个链接；
			if (pageObject.getTotlePage() <= showNum) {
				startNum = 1;
				endNum = pageObject.getTotlePage();
			} else {				
				startNum=(pageObject.getCurrentPageNum()-showNum>0)?
						pageObject.getCurrentPageNum()-showNum:1;
				
				endNum =(pageObject.getCurrentPageNum() + showNum > pageObject.getTotlePage()) 
				? pageObject.getTotlePage()	: pageObject.getCurrentPageNum() + showNum;
			}
			//循环打印
			for (int i = startNum; i <= endNum; i++) {
				if(i==pageObject.getCurrentPageNum()){
					out.println(i);
					continue;
				}
				out.println("<a href='" + actionUrl + "?selectPageNum="+i+ "'>" + i + "</a>");
			}
			
			//前一页&后一页
			String prevPage=(pageObject.getCurrentPageNum()>1)?"<a href='" + actionUrl + "?selectPageNum="+(pageObject.getCurrentPageNum()-1)+"'>prev</a>":"prev";
			String afterPage=(pageObject.getCurrentPageNum()<pageObject.getTotlePage())?"<a href='" + actionUrl + "?selectPageNum="+(pageObject.getCurrentPageNum()+1)+"'>after</a>":"after";
			
			out.println("["+prevPage+"&nbsp;/&nbsp;"+afterPage+"]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	protected String convertHtml(String str) {

		ActionMapping mapping = (ActionMapping) ActionContext.getContext().get("struts.actionMapping");
		
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

		return  request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()+request.getContextPath().toString()+mapping.getNamespace()+"/"+str+".action";

	}

	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	public HibernatePage getPageObject() {
		return pageObject;
	}

	public void setPageObject(HibernatePage pageObject) {
		this.pageObject = pageObject;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

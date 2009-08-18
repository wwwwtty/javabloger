package com.exam.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

/*
 *@auther heshencao 
 * 2009-8-15 上午01:41:27
 */
public class BaseAction extends ActionSupport{
	
	private String target;
	private List  list;
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}

package org.cms.dao.impl;

import java.util.HashMap;

public final class PramasMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	private int firstNum;
	private int limiNum;
	private Class<?> Entity;
	
	public int getFirstNum() {
		return firstNum;
	}
	public void setFirstNum(int firstNum) {
		this.firstNum = firstNum;
	}
	public int getLimiNum() {
		return limiNum;
	}
	public void setLimiNum(int limiNum) {
		this.limiNum = limiNum;
	}
	public Class<?> getEntity() {
		return Entity;
	}
	public void setEntity(Class<?> entity) {
		Entity = entity;
	}
	
}

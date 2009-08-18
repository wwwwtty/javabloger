package com.exam.dao;

public class SQLRestraint {

	private int maxResult;
	private int firstResult;
	private Class entity;
	
	
	public int getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public Class getEntity() {
		return entity;
	}
	public void setEntity(Class entity) {
		this.entity = entity;
	}
}

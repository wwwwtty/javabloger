package org.cms.dao;

import java.io.Serializable;
import java.util.Map;

/*
 *@auther heshencao 
 * 2010-1-17 下午07:25:12
 */
public class QueryPramater {

	private String hqlName;
	private String hql;
	private Class<Serializable> Entity;
	private Map<String,Object> pramas;
	private int startNum ;
	private int limitNum;
	
	public String getHqlName() {
		return hqlName;
	}
	public void setHqlName(String queryName) {
		this.hqlName = queryName;
	}
	public String getHql() {
		return hql;
	}
	public void setHql(String query) {
		this.hql = query;
	}
	public Class<Serializable> getEntity() {
		return Entity;
	}
	public void setEntity(Class<Serializable> entity) {
		Entity = entity;
	}
	public Map<String, Object> getPramas() {
		return pramas;
	}
	public void setPramas(Map<String, Object> pramas) {
		this.pramas = pramas;
	}
	public int getStartNum() {
		return startNum>0?startNum:null;
	}
	public void setStartNum(Integer startNum) {
			this.startNum = startNum;
	}
	public int getLimitNum() {
		return limitNum>0?limitNum:null;
	}
	public void setLimitNum(Integer limitNum) {
			this.limitNum = limitNum;
	}
}

package org.hsc.novelSpider.dao.core;

import java.io.Serializable;


/**
 * Data access interface for domain model
 * @author MyEclipse Persistence Tools
 */
public interface IBaseHibernateDAO<T> {
	
	public T findById(Serializable id) ;
	
	/**保存<br>
	 * <b>主键都统一用整数</b>
	 * @param transientInstance
	 * @return
	 */
	public Integer save(T transientInstance);

	public void delete(T persistentInstance);
	
	public void deleteByID(Serializable id);
	
	public T merge(T detachedInstance);
	
	public void saveOrUpdate(T detachedInstance) ;
	
	public void update(T detachedInstance) ;
	
}
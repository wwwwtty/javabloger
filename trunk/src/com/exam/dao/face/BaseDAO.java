package com.exam.dao.face;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.exam.dao.SQLRestraint;

/**
 * @author heshencao,2009-8-14 上午01:38:17
 *
 */
public interface BaseDAO<T> {
	
	void delete(T persistentInstance);
	
	/**通过ID主键删除对应记录；
	 * @param ID
	 */
	void deleteByID(Serializable ID);
	
	/**通过ID查询；
	 * @param id
	 * @return
	 */
	public T findById(Serializable id) ;
	
	List<T> findByExample(T instance) ;

	/**供子类调用的模板方法；
	 * @param propertyName
	 * @param value
	 * @return
	 */
	List<T> findByProperty(String propertyName, Object value) ;

	/**查询当前的全部记录；
	 * @return
	 */
	List<T> findAll() ;

	T merge(T instance) ;
	
	void update(T instance) ;
	
	/**自定义HQL接口；可提供事务支持；
	 */
	public Object executeHQL(String queryString,String[] parameters);
	
	public Object executeSQL(final String queryString,final String[] parameters);
	
	/* 
	 * @see com.exam.dao.face.BaseDAO#executeHQL(java.lang.String, java.lang.String[],SQLRestraint)
	 */
	public List<T> findByHql(final String queryString, final Object[] values);
	
	/* 
	 * @see com.exam.dao.face.BaseDAO#executeHQL(java.lang.String, java.lang.String[],SQLRestraint)
	 */
	public List<T> findBySql(final String queryString, final Object[] values);
	
	/**
	 * 自定义查询HQL语句；不支持事务；
	 */
	public List<T> findByHql(final String queryString, final Object[] values,final SQLRestraint restraint) ;
	
	public List<T> findBySql(final String queryString, final Object[] values,final SQLRestraint restraint);
	
	
	/**保存
	 * @param instance
	 */
	void save(T instance) ;
	
	public Collection <T> addFilter(String filter,Collection  collectioin);
}

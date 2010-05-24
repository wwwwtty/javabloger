package org.cms.dao;


import java.io.Serializable;
import java.util.List;

import org.cms.dao.impl.PramasMap;
import org.hibernate.criterion.DetachedCriteria;

public interface BaseDAO {
	
	//@Transactional(propagation=Propagation.MANDATORY)
	void delete(Object object,Class<? extends Serializable> clazz);
	
	/**通过ID主键删除对应记录；
	 * @param ID
	 */
	//@Transactional(propagation=Propagation.MANDATORY)
	void deleteByID(Serializable ID,Class<? extends Serializable> clazz);
	
	/**通过ID查询；
	 * @param id
	 * @return
	 */
	public Object findById(Serializable id,Class<? extends Serializable> clazz) ;
	/**通过属性查询；
	 * @param id
	 * @return
	 */
	public List<? extends Serializable> findByField(String filed,Object value,Class<? extends Serializable> clazz) ;
	/**查询全部；
	 * @param id
	 * @return
	 */
	public List<? extends Serializable> findAll(Class<? extends Serializable> clazz) ;
	/**保存
	 * @param instance
	 */
	//@Transactional(propagation=Propagation.MANDATORY)
	public Serializable save(Object instance) ;


	/**更新操作
	 * @param instance
	 * @return
	 */
	//@Transactional(propagation=Propagation.MANDATORY)
	public Object merge(Object instance) ;
	
	/**更新操作
	 * @param instance
	 * @return
	 */
	//@Transactional(propagation=Propagation.MANDATORY)
	public void update(Object instance) ;
	
	/**自定义HQL接口；可提供事务支持；
	 * @param queryString
	 * @param parameters
	 * @return
	 */
	public Object executeHQL(String queryString,String[] parameters);
	
	public Object executeSQL(final String queryString,final String[] parameters);
	
	
	/**自定义查询HQL语句 */
	public List<? extends Serializable> findByHql(final String queryString);
	
	/**自定义查询HQL语句 */
	public List<? extends Serializable> findByHql(final String queryString,final PramasMap values);
	
	
	/**自定义查询HQL语句；不支持事务；
	 * @param queryString
	 * @param values
	 * @return
	 */
	public List<? extends Serializable> findBySql(final String queryString, Class<?> clazz);

	/**自定义查询HQL语句；不支持事务；
	 * @param queryString
	 * @param values
	 * @return
	 */
	public List<? extends Serializable> findBySql(final String queryString, final PramasMap values);
	
	/**通过在配置文件中预定义的SQL串名字查询结果集;
	 * @param queryName 配置文件中的SQL名称;
	 * @param nameParas 传入的参数集
	 * @return
	 */
	public List<? extends Serializable> findByNamedQueryParas(final String queryName, final PramasMap nameParas) ;
	
	/***通过在配置文件中预定义的SQL串名字查询结果集;
	 * @param queryName 配置文件中的SQL名称;
	 * @return
	 */
	public List<? extends Serializable> findByNamedQuery(final String queryName);
	
	public List<? extends Serializable> findByNamedQuery(final String queryName,Class<?> clazz);
	
	/**通过在配置文件中预定义的SQL(增,删,改)
	 * @param queryName
	 * @param queryMap
	 * @return
	 */
	public int excuteNamedUpdate(final String queryName, final PramasMap queryMap);
	
	public List<? extends Serializable> findByCriteria (DetachedCriteria criteria);
}

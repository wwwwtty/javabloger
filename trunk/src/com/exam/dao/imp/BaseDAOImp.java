package com.exam.dao.imp;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.exam.dao.SQLRestraint;
import com.exam.dao.face.BaseDAO;

public class BaseDAOImp<T> implements BaseDAO<T>{
    
    private HibernateTemplate templet;	
	private Class clazz=null;
	private String clazzName="";
	protected static Logger log=null;
	{
	    if(clazz==null){
		//获取子类的的泛型实现的Class类对象，
			clazz=(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			clazzName=clazz.getSimpleName();
			if(log==null)
				log=Logger.getLogger(clazzName);
		}
	}
	
	/* 将对象持久化；
	 * @see org.cms.persistence.dao.BaseDAO#save(java.lang.Object)
	 */
	public void save(T instance) {
	    if(log.isDebugEnabled()){
	    	log.debug("saving "+clazzName+" instance");
	    }
	    templet.save(instance);
	}

	/*删除数据；
	 * @see org.cms.persistence.dao.BaseDAO#delete(java.lang.Object)
	 */
	public void delete(T persistentInstance) {
	    if(log.isDebugEnabled()){
	    	log.debug("deleting "+clazzName+" instance");
	    }
	    templet.delete(persistentInstance);
	}
	
	/*供子类调用的模板方法；
	 * @param property
	 * @param value
	 */
	public void deleteByProperty(final String property,final String value) {
	    if(log.isDebugEnabled()){
	    	log.debug("deleting "+clazzName+" instance. "+property+" : "+value);
	    }
	    templet.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
			    String hql="delete from "+clazzName+" where "+property+"=:value";
			    Query query=session.createQuery(hql);
			    query.setString("value", value);
			    return query.executeUpdate();
			}
	    });
	}
	
	/* 通过ID主键删除对应记录；由于主键名称的不确定。交由子类实现；
	 * @see org.cms.persistence.dao.BaseDAO#deleteByID(java.lang.String)
	 */
	public void deleteByID(Serializable ID){
		if(log.isDebugEnabled()){
	    	log.debug("delete "+clazzName+" instance by ID");
	    }
	    Object object=this.findById(ID);
	    templet.delete(object);
	}
	
	/* 通过ID查询；
	 * @see org.cms.persistence.dao.BaseDAO#findById(java.lang.String)
	 */
	public T findById(Serializable id) {
	    if(log.isDebugEnabled()){
	    	log.debug("getting "+clazzName+" instance with id: " + id);
	    }
	    return (T) templet.get(clazz, id);
	}

	public List<T> findByExample(T instance) {
	    if(log.isDebugEnabled()){
	    	log.debug("finding "+clazzName+" instance by example");
	    }
	    return templet.findByExample(instance);	    	
	}

	/* 子类模板方法；
	 * @see org.cms.persistence.dao.BaseDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List<T> findByProperty(String propertyName, Object value) {
	    if(log.isDebugEnabled()){
	    	log.debug("finding "+clazzName+" instance with property: "+propertyName+ ", value: " + value);
	    }
	    String queryString = "from "+clazzName+" as model where model."+ propertyName + "= ?";
	    return templet.find(queryString, value);
	}

	/* 查询当前的全部记录；
	 * @see org.cms.persistence.dao.BaseDAO#findAll()
	 */
	public List<T> findAll() {
	    if(log.isDebugEnabled()){
	    	log.debug("finding all "+clazzName+" instances");
	    }
	    String queryString = "from "+clazzName;
	    return templet.find(queryString);
	}

	/* 存储或更新数据，当数据不存在时，类似save方法,更新时类似update
	 * @see org.cms.persistence.dao.BaseDAO#merge(java.lang.Object)
	 */
	public T merge(T instance) {
	    if(log.isDebugEnabled()){
	    	log.debug("merging "+clazzName+" instance");
	    }
	    T result = (T) templet.merge(instance);
	    log.debug("merge successful");
	    return result;
	}
	
	/* 更新数据
	 * @see org.cms.persistence.dao.BaseDAO#update(java.lang.Object)
	 */
	public void update(T instance) {
	    if(log.isDebugEnabled()){
	    	log.debug("update "+clazzName+" instance; ");
	    }	  
	    templet.update(instance);
	    log.debug("update successful");
	    
	}
	
	/* 自定义查询HQL语句；不支持事务；
	 * @see org.cms.persistence.dao.BaseDAO#findByHql(java.lang.String, java.lang.Object[])
	 */
	public List<T> findByHql(final String queryString, final Object[] values) throws DataAccessException {
	    return findByHql(queryString, values, null);
	}	
	
	/*自定义查询HQL语句；不支持事务；
	 * @see org.cms.persistence.dao.BaseDAO#findByHql(java.lang.String, java.lang.Object[])
	 * @param queryString
	 * @param values
	 * @param startNum 如果小于1 默认为首行数据开始；
	 * @param limitNum 如果小于1 默认为查询全部数据；
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> findByHql(final String queryString, final Object[] values,final SQLRestraint restraint) throws DataAccessException {
	    if(log.isDebugEnabled()){log.debug(clazzName+" query:"+queryString);}
		
	    return (List<T>) templet.executeWithNativeSession(new HibernateCallback() {
		public Object doInHibernate(final Session session) throws HibernateException {
		    final Query queryObject = session.createQuery(queryString);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
            	queryObject.setParameter(i, values[i]);
                }
            }
            if(null!=restraint){
	            if(restraint.getFirstResult()>0){
	                queryObject.setFirstResult(restraint.getFirstResult());
	            }
	            if(restraint.getMaxResult()>0){
	                queryObject.setMaxResults(restraint.getMaxResult());
	            }
            }
            
		    return queryObject.list();
		}
		});	
	}


	public List<T> findBySql(String queryString, Object[] values) {
		return findBySql(queryString,values,null);
	}
	/* 自定义查询SQL语句；不支持事务；
	 * @see org.cms.persistence.dao.BaseDAO#findBySql(java.lang.String, java.lang.Object[])
	 */
	public List<T> findBySql(final String queryString, final Object[] values ,final SQLRestraint restraint) throws DataAccessException {
	    if(log.isDebugEnabled()){
		log.debug(clazzName+" query:"+queryString);
	    }
		
	    return (List<T>) templet.executeWithNativeSession(new HibernateCallback() {
		public Object doInHibernate(final Session session) throws HibernateException {
		    final SQLQuery queryObject = session.createSQLQuery(queryString);
		    if (values != null) {
                for (int i = 0; i < values.length; i++) {
            	queryObject.setParameter(i, values[i]);
                }
            }
		    if(null!=restraint){
				    if(restraint.getFirstResult()>0){
		                queryObject.setFirstResult(restraint.getFirstResult());
		            }
		            if(restraint.getMaxResult()>0){
		                queryObject.setMaxResults(restraint.getMaxResult());
		            }
		            if(restraint.getEntity()!=null){
		            	queryObject.addEntity(restraint.getEntity());
		            }
		    }
		    return queryObject.list();
		}		
	    });
	}
	
	/* 自定义HQL接口；可提供事务支持；
	 * @see org.cms.persistence.dao.BaseDAO#executeHQL(java.lang.String, java.lang.String[])
	 */
	public Object executeHQL(final String queryString,final String[] parameters){
	    if(log.isDebugEnabled()){
		log.debug(clazzName+" execute:"+queryString);
	    }
				
	    return templet.executeWithNativeSession(new HibernateCallback() {
		public Object doInHibernate(Session session) throws HibernateException {
		    Query queryObject = session.createQuery(queryString);
		    if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
			    queryObject.setParameter(i, parameters[i]);
			}
		    }
		    return queryObject.executeUpdate();
		}
	    });
	}
	
	/* 自定义SQL接口；可提供事务支持；
	 * @see org.cms.persistence.dao.BaseDAO#executeSQL(java.lang.String, java.lang.String[])
	 */
	public Object executeSQL(final String queryString,final String[] parameters){
	    if(log.isDebugEnabled()){
		log.debug(clazzName+" execute:"+queryString);
	    }
				
	    return templet.executeWithNativeSession(new HibernateCallback() {
		public Object doInHibernate(Session session) throws HibernateException {
		    Query queryObject = session.createSQLQuery(queryString);
		    if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
			    queryObject.setParameter(i, parameters[i]);
    			}
		    }
		    return queryObject.executeUpdate();
		}
	    });
	}
	
	/* 属性过滤器；
	 * @see org.cms.persistence.dao.BaseDAO#addFilter(java.lang.String, java.util.Collection)
	 */
	public Collection <T> addFilter(String filter,Collection  collectioin){
	    return templet.getSessionFactory().openSession().createFilter(collectioin, filter).list();
	}
	//setter,,,getter....	
	public HibernateTemplate getTemplet() {
	    return templet;
	}

	public void setTemplet(HibernateTemplate templet) {
	    this.templet = templet;
	}


}

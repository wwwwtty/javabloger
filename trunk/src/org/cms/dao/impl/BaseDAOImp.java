package org.cms.dao.impl;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.cms.dao.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class BaseDAOImp implements BaseDAO{
    
    private HibernateTemplate templet;	
	//private Class clazz=null;
	//private String clazzName="";
	protected static Logger log=Logger.getLogger(BaseDAOImp.class);

	/* 将对象持久化；
	 */
	@Override
	public Serializable save(Object instance) {
	    if(log.isDebugEnabled()){
		log.debug("saving "+instance.getClass().getName()+" instance");
	    }
	    return templet.save(instance);
	}

	/*删除数据；
	 */
	@Override
	public void delete(Object p,Class<? extends Serializable> clazz) {
	    if(log.isDebugEnabled()){
		log.debug("deleting "+p.getClass().getName()+" instance");
	    }
	    templet.delete(p);
	}

	/* 通过ID主键删除对应记录；由于主键名称的不确定。交由子类实现；
	 * @see org.cms.persistence.dao.BaseDAO#deleteByID(java.lang.String)
	 */
	@Override
	public void deleteByID(Serializable id,Class<? extends Serializable> clazz){
		 if(log.isDebugEnabled()){
			log.debug("delete "+clazz.getName()+" instance with id: " + id);
		 }
			  templet.delete(templet.get(clazz, id));
	}
	@SuppressWarnings("unchecked")
	public List<? extends Serializable> findByField(final String filed,final Object value,final Class<? extends Serializable> clazz){
		 return (List<? extends Serializable>) templet.executeWithNativeSession((HibernateCallback<? extends Serializable>) new HibernateCallback() {
				public List<? extends Serializable> doInHibernate(final Session session) throws HibernateException {
					final Criteria ca=session.createCriteria(clazz);
					ca.add(Restrictions.eq(filed, value));
					return ca.list();
				}
		    });
		
	}
	/* 通过ID查询；
	 */
	@Override
	public Object findById(Serializable id,Class<? extends Serializable> clazz) {
	    if(log.isDebugEnabled()){
		log.debug("getting "+clazz.getName()+" instance with id: " + id);
	    }
	    return templet.get(clazz, (Serializable) id);
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends Serializable> findAll(Class<? extends Serializable> clazz){
		if(log.isDebugEnabled()){
			log.debug("查询全部信息: "+clazz.getName());
		    }
		return templet.find("from "+clazz.getName());
	}

	/** 存储或更新数据，当数据不存在时，类似save方法,更新时类似update
	 */
	@Override
	public Object merge(Object instance) {
	    if(log.isDebugEnabled()){
		log.debug("merging "+instance.getClass().getName()+" instance");
	    }
	    Object result = templet.merge(instance);
	    log.debug("merge successful");
	    return result;
	}
	
	/** 更新数据
	 */
	@Override
	public void update(Object instance) {
	    if(log.isDebugEnabled()){
		log.debug("update "+instance.getClass().getName()+" instance; ");
	    }	  
	    templet.update(instance);
	    log.debug("update successful");
	    
	}
	public List<? extends Serializable> findByCriteria (DetachedCriteria criteria){
		return this.templet.findByCriteria(criteria);
	}
	@Override
	public List findByHql(String queryString) throws DataAccessException {
	    return this.findByHql(queryString,null);
	}
	
	/** 自定义查询HQL语句；不支持事务；
	 */
	@Override
	public List findByHql(final String queryString,final PramasMap Paras) throws DataAccessException {
		 if(log.isDebugEnabled()){
				log.debug(" query:"+queryString);
			    }
			    return (List) templet.executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(final Session session) throws HibernateException {
				    final Query queryObject = session.createQuery(queryString);
				    if (Paras != null) {
				    	Set<String> ks=Paras.keySet();
				    	for(String k:ks){
				    		queryObject.setParameter(k, Paras.get(k));
				    	}
		            }
		            if(Paras.getFirstNum()>0){
		                queryObject.setFirstResult(Paras.getFirstNum());
		            }
		            if(Paras.getLimiNum()>0){
		                queryObject.setMaxResults(Paras.getLimiNum());
		            }
				    return queryObject.list();
				}		
			    });
	}	
	
	@Override
	public List findBySql(final String queryString, final PramasMap Paras) throws DataAccessException {
	    if(log.isDebugEnabled()){
		log.debug(" query:"+queryString);
	    }
	    return (List) templet.executeWithNativeSession(new HibernateCallback() {
		public Object doInHibernate(final Session session) throws HibernateException {
		    final SQLQuery queryObject = session.createSQLQuery(queryString);
		    if (Paras != null) {
		    	Set<String> ks=Paras.keySet();
		    	for(String k:ks){
		    		queryObject.setParameter(k, Paras.get(k));
		    	}
            }
            if(Paras.getFirstNum()>0){
                queryObject.setFirstResult(Paras.getFirstNum());
            }
            if(Paras.getLimiNum()>0){
                queryObject.setMaxResults(Paras.getLimiNum());
            }
            if(Paras.getEntity()!=null){
            	queryObject.addEntity(Paras.getEntity());
            }
		    return queryObject.list();
		}		
	    });
	}
	
	@Override
	public Object executeHQL(final String queryString,final String[] parameters){
	    if(log.isDebugEnabled()){
		log.debug(" execute:"+queryString);
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
	
	@Override
	public Object executeSQL(final String queryString,final String[] parameters){
	    if(log.isDebugEnabled()){
		log.debug(" execute:"+queryString);
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
	

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Serializable> findByNamedQueryParas(final String queryName, final PramasMap paras) {
		return (List<? extends Serializable>) templet.executeWithNativeSession(
				new HibernateNamedQueryUtils(queryName,paras,HibernateNamedQueryUtils.QUERY)		
		);
	}
	@Override
	public List<? extends Serializable> findByNamedQuery(final String queryName,Class<?> clazz){
		PramasMap pramas=new PramasMap();
		pramas.setEntity(clazz);
		return this.findByNamedQueryParas(queryName, pramas);
	}
	@Override
	public List<? extends Serializable> findByNamedQuery(final String queryName) {
		return (List) templet.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				return session.getNamedQuery(queryName).list();
			}
		});
	}

	@Override
	public int excuteNamedUpdate(final String queryName,  final PramasMap paras) {
		return (Integer) templet.executeWithNativeSession(
				new HibernateNamedQueryUtils(queryName,paras,HibernateNamedQueryUtils.UPDATE)
		);
	}
	
	
	
	//setter,,,getter....	
	public HibernateTemplate getTemplet() {
	    return templet;
	}

	public void setTemplet(HibernateTemplate templet) {
	    this.templet = templet;
	}

}

class HibernateNamedQueryUtils implements HibernateCallback{
	public static int QUERY=1;
	public static int UPDATE=2;
	public static int DELETE=3;
	
	private String queryName;
	private PramasMap queryMap;
	private int queryType;

	
	public HibernateNamedQueryUtils(String queryName,PramasMap queryMap,int queryType){
		this.queryMap=queryMap;
		this.queryName=queryName;
		this.queryType=queryType;
	}
	
	@Override
	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		//实例转换为可用对象;
		Query query = session.getNamedQuery(queryName).setResultTransformer(new BeanTransformer(queryMap.getEntity()));
//		Query query = session.getNamedQuery(queryName).setResultTransformer(Transformers.aliasToBean(queryMap.getEntity()));
		
		String[] namedParam = query.getNamedParameters();
		if (queryMap != null) {
			if (queryMap.size() > 0) {
				for (Iterator<String> iter = queryMap.keySet().iterator(); iter
						.hasNext();) {
					String key = iter.next();
					if (ArrayUtils.contains(namedParam, key)) {
						Object obj = queryMap.get(key);
						if (obj == null) {
							query.setParameter(key, null);
						} else if (obj.getClass().isArray()) {
							Object[] objArr = (Object[]) obj;
							if (objArr.length > 0)
								query.setParameterList(key, objArr);
							else
								query.setParameter(key, "");
						} else if (obj instanceof Collection) {
							Collection objColl = (Collection) obj;
							if (objColl.size() > 0)
								query.setParameterList(key, objColl);
							else
								query.setParameter(key, "");
						} else if (obj instanceof Date) {
							query.setTimestamp(key, (Date) obj);
						} else {
							query.setParameter(key, obj);
						}
					}

				}
			}
			if (queryMap.getFirstNum() > 0) {
				query.setFirstResult(queryMap.getFirstNum());
			}
			if (queryMap.getLimiNum() > 0) {
				query.setMaxResults(queryMap.getLimiNum());
			}
//			if (queryMap.getEntity() != null && this.queryType == QUERY) {
//				query.addEntity(queryMap.getEntity());
//			}
		}

		// 返回结果;
		if (this.queryType == this.UPDATE) {
			return query.executeUpdate();
		}
		if (this.queryType == this.QUERY) {
			return query.list();
		}
		if (this.queryType == this.DELETE) {
			return query.executeUpdate();
		} else {
			return query.list();
		}
	}
}

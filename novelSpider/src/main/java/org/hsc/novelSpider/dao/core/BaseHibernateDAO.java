package org.hsc.novelSpider.dao.core;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.hsc.novelSpider.dao.utils.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
public abstract class BaseHibernateDAO<T>  implements IBaseHibernateDAO<T> {
	private static final Logger log = LoggerFactory.getLogger(BaseHibernateDAO.class);
	private Class<T> entityClass;
	@Autowired(required=true) private SessionFactory sessionFactory;
    protected DynamicSQLBuilder dynamicStatementBuilder;  
	
	public BaseHibernateDAO() {
		 Class<?> c = getClass();
	     Type t = c.getGenericSuperclass();
	     if (t instanceof ParameterizedType) {
	          Type[] p = ((ParameterizedType) t).getActualTypeArguments();
	          this.entityClass = (Class<T>) p[0];
	     }
	}
	
	protected <L> L doExecute(HibernateCallback<L> action){
		Session session;
		boolean isnew=false;
		// SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
		try{ 
			session=sessionFactory.getCurrentSession();
		}catch(HibernateException e){
			session=sessionFactory.openSession();
			isnew=true;
		}
		
		try{
			return action.doInHibernate(session);
		}
		finally{
			if(isnew){
				session.close();
			}
		}
	}
	
	
	@Override
	public T findById(final Serializable id) {
		log.debug("getting instance with id: " + id);
		return doExecute(new HibernateCallback<T>() {
			
			@Override @SuppressWarnings("unchecked")
			public T doInHibernate(Session session) {
				return (T) session.get(entityClass,id);
			}
		});
	}

	/** (non-Javadoc)
	 * @see com.ty.core.dao.IBaseHibernateDAO#save(java.lang.Object)
	 */
	@Override
	
	public Integer save(final T transientInstance) {
		log.debug("saving instance");
		return doExecute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session){
				return (Integer) session.save(transientInstance);
			}
		});
	}
	
	public void saveBate(final List<T> list) {
		doExecute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) {
				for(T t :list){
				  session.save(t);
				}
				return null;
			}
		});
	}

	@Override
	
	public void delete(final T t) {
		log.debug("deleting instance");
		doExecute(new HibernateCallback<Object>() {
			@Override
			public Serializable doInHibernate(Session session){
				session.delete(t);
				return null;
			}
		});
	}

	@Override
	
	public void deleteByID(final Serializable id) {
		doExecute(new HibernateCallback<Object>() {
			@Override
			public Serializable doInHibernate(Session session) {
				session.delete(session.get(BaseHibernateDAO.this.entityClass, id));
				return null;
			}
		});
	}
	
	@Override
	
	public T merge(final T t) {
		log.debug("merging instance");
		return doExecute(new HibernateCallback<T>() {
			@Override @SuppressWarnings("unchecked")
			public T doInHibernate(Session session){
				return (T) session.merge(t);
			}
		});
	}
	@Override
	
	public void saveOrUpdate(final T t) {
		log.debug("saveOrUpdate instance");
		doExecute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session){
				 session.saveOrUpdate(t);
				 return null;
			}
		});
	}
	@Override
	
	public void update(final T t) {
		log.debug("update instance");
		doExecute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session){
				 session.update(t);
				 return null;
			}
		});
	}
	
	protected int countQuery(final DetachedCriteria query){
		
		return doExecute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session){
				query.setProjection(Projections.rowCount());
				Criteria criteria = query.getExecutableCriteria(session);
				int result = ((Long) criteria.uniqueResult()).intValue();
				query.setProjection(null);
				return result;
			}
		});
		
		
	}
	
	protected List<T> queryPage(final DetachedCriteria query,final Pager<T> pager){
		
		return doExecute(new HibernateCallback<List<T>>() {
			@Override @SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session){
				Criteria criteria = query.getExecutableCriteria(session);
				
				pager.process();
				
				if(pager.getStartRow()>0){
					criteria.setFirstResult(pager.getStartRow());
				}
				if(pager.getPageSize()>0){
					criteria.setMaxResults(pager.getPageSize());
				}
				criteria.setProjection(null);
				return criteria.list();
			}
		});
	}
	
	protected  <L> List<L> queryPageBO(final DetachedCriteria query,final Pager<?> pager,final Class<L> clazz){
		return doExecute(new HibernateCallback<List<L>>() {
			@Override @SuppressWarnings("unchecked")
			public List<L> doInHibernate(Session session){
				Criteria criteria = query.getExecutableCriteria(session);
				if(pager.getStartRow()>0){
					criteria.setFirstResult(pager.getStartRow());
				}
				if(pager.getPageSize()>0){
					criteria.setMaxResults(pager.getPageSize());
				}
				criteria.setProjection(null);
				
				criteria.setResultTransformer(Transformers.aliasToBean(clazz));
//				criteria.setResultTransformer(Criteria.ROOT_ENTITY);
				
				return criteria.list();
			}
		});
	}
	
	protected List<T> query(final DetachedCriteria query){
		
		return doExecute(new HibernateCallback<List<T>>() {
			@Override @SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session){
				Criteria criteria = query.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}
	
    
	protected  <L> Pager<L> findPagerByNamedSQL(final String queryName,final Pager<L> pager,final Map<String, ?> parameters,final Class<L> clazz){
			
		return doExecute(new HibernateCallback<Pager<L>>() {
			@Override @SuppressWarnings("unchecked")
			public Pager<L> doInHibernate(Session session){
			    String sql=BaseHibernateDAO.this.dynamicStatementBuilder.getSQL(queryName,parameters);
			   log.debug("SQL Template【"+queryName+"】");
			    
			    SQLQuery query=session.createSQLQuery(sql);
			    //查询记录
		    	if (parameters != null) {  
		    		 query.setProperties(parameters);  
		    	}
		    	
		    	query.setResultTransformer(ToBeanResultTransformer.aliase(clazz));
		    	
			    if(pager!=null){
			    	
					//统计记录数
					SQLQuery cq=session.createSQLQuery("select count(*) from ("+sql+") COUNT");
					if (parameters != null) {  
						cq.setProperties(parameters);  
				   	}
					pager.setTotalRows(((BigInteger)cq.uniqueResult()).intValue());
					
					pager.process();
					
					
					if(pager.getStartRow()>0){
						query.setFirstResult(pager.getStartRow());
					}
					if(pager.getPageSize()>0){
						query.setMaxResults(pager.getPageSize());
					}
					
							
					pager.setData(query.list());
					
					return pager;
			    }else{
			    	return Pager.builder(query.list());
			    }
			}
		});
	}
	
	
	protected  <L> List<L> findByNamedSQL(final String queryName,final Map<String, ?> parameters,final Class<L> clazz){
	   
		
		return doExecute(new HibernateCallback<List<L>>() {
			@Override @SuppressWarnings("unchecked")
			public List<L> doInHibernate(Session session){
				 String sql=BaseHibernateDAO.this.dynamicStatementBuilder.getSQL(queryName,parameters);
				    log.debug("SQL Template【"+queryName+"】:");
					
					//查询记录
					SQLQuery query=session.createSQLQuery(sql);
			    	if (parameters != null) {  
			    		 query.setProperties(parameters);  
			    	}
					
					query.setResultTransformer(ToBeanResultTransformer.aliase(clazz));		
					
					return query.list();
			}
		});
	}
	
	protected  <L> List<L> findBySQL(final String sql,final Map<String, ?> parameters,final Class<L> clazz){
		return doExecute(new HibernateCallback<List<L>>() {
			@Override @SuppressWarnings("unchecked")
			public List<L> doInHibernate(Session session){
					//查询记录
					SQLQuery query=session.createSQLQuery(sql);
			    	if (parameters != null) {  
			    		 query.setProperties(parameters);  
			    	}
					query.setResultTransformer(ToBeanResultTransformer.aliase(clazz));		
					
					return query.list();
			}
		});
	}
	protected  <L> List<L> findByHQL(final String sql,final Map<String, ?> parameters,final Class<L> clazz){
		return doExecute(new HibernateCallback<List<L>>() {
			@Override @SuppressWarnings("unchecked")
			public List<L> doInHibernate(Session session){
					//查询记录
				Query query=session.createQuery(sql);
			    if (parameters != null) {  
			   		 query.setProperties(parameters);  
			   	}
				return query.list();
			}
		});
	}
	protected  <L> Pager<L> findPagerByHQL(final String hql,final Pager<L> pager,final Map<String, ?> parameters,final Class<L> clazz){
		return doExecute(new HibernateCallback<Pager<L>>() {
			@Override @SuppressWarnings("unchecked")
			public Pager<L> doInHibernate(Session session){
				//查询记录
				Query query=session.createQuery(hql);
		    	if (parameters != null) {  
		    		 query.setProperties(parameters);  
		    	}
				query.setResultTransformer(ToBeanResultTransformer.aliase(clazz));		
				
				 if(pager!=null){
						//统计记录数
						Query cq=session.createQuery("select count(*) from ("+hql+") COUNT");
						if (parameters != null) {  
							cq.setProperties(parameters);  
					   	}
						pager.setTotalRows(((BigInteger)cq.uniqueResult()).intValue());

						pager.process();
						
						if(pager.getStartRow()>0){
							query.setFirstResult(pager.getStartRow());
						}
						if(pager.getPageSize()>0){
							query.setMaxResults(pager.getPageSize());
						}
								
						pager.setData(query.list());
						
						return pager;
				  }else{
				    	return Pager.builder(query.list());
				  }
			}
		});
	}
	protected  <L> Pager<L> findPagerBySQL(final String sql,final Pager<L> pager,final Map<String, ?> parameters,final Class<L> clazz){
		return doExecute(new HibernateCallback<Pager<L>>() {
			@Override @SuppressWarnings("unchecked")
			public Pager<L> doInHibernate(Session session){
					//查询记录
					SQLQuery query=session.createSQLQuery(sql);
			    	if (parameters != null) {  
			    		 query.setProperties(parameters);  
			    	}
					query.setResultTransformer(ToBeanResultTransformer.aliase(clazz));		
					
					 if(pager!=null){
							//统计记录数
							SQLQuery cq=session.createSQLQuery("select count(*) from ("+sql+") COUNT");
							if (parameters != null) {  
								cq.setProperties(parameters);  
						   	}
							pager.setTotalRows(((BigInteger)cq.uniqueResult()).intValue());

							pager.process();
							
							if(pager.getStartRow()>0){
								query.setFirstResult(pager.getStartRow());
							}
							if(pager.getPageSize()>0){
								query.setMaxResults(pager.getPageSize());
							}
									
							pager.setData(query.list());
							
							return pager;
					  }else{
					    	return Pager.builder(query.list());
					  }
			}
		});
	}
	
}
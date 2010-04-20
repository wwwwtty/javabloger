package org.cms.service.core.imp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.cms.dao.BaseDAO;
import org.cms.doamin.auth.Function;
import org.cms.service.core.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImp<T> implements BaseService<T> {

	@Autowired
	protected BaseDAO baseDao;//注入DAO
	
	public BaseDAO getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	private  Class<? extends Serializable> entityClass;//泛型的类对象;
	  public  BaseServiceImp() {
	        entityClass  = (Class<? extends Serializable>) ((ParameterizedType) getClass()
	                                .getGenericSuperclass()).getActualTypeArguments()[0];
	    }
	@Override
	public List<T> findAll() {
		return (List<T>)baseDao.findAll(entityClass);
	}

	@Override
	public T findById(String id) {
		return (T) baseDao.findById(id, entityClass);
	}

	@Override
	public String save(T t) {
		return (String) baseDao.save(t);
	}

	@Override
	public void update(T t) {
		baseDao.merge(t);
	}
	public List<T> findValid(){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(entityClass);
		detachedCriteria.add(Restrictions.eq("enabled", true));
		return (List<T>) this.baseDao.findByCriteria(detachedCriteria);
	}
}

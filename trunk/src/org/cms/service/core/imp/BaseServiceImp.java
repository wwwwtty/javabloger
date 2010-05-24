package org.cms.service.core.imp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.cms.dao.BaseDAO;
import org.cms.doamin.auth.Function;
import org.cms.service.core.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public class BaseServiceImp<T> implements BaseService<T> {
	
	protected Logger log=LoggerFactory.getLogger(getClass());
	
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
		return this.findAll(FIND_ALL);
	}

	@Override
	public T findById(String id) {
		return (T) baseDao.findById(id, entityClass);
	}

	@Override
//	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String save(T t) {
		return (String) baseDao.save(t);
	}

	@Override
	public void update(T t) {
		baseDao.merge(t);
	}
	public List<T> findAll(String valid){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(entityClass);
		if("Y".equals(valid)||"N".equals(valid)){
			detachedCriteria.add(Restrictions.eq("enabled", valid));
		}else if(!"A".equals(valid)){
			log.warn("查询参数不足...");
		}
		return (List<T>) this.baseDao.findByCriteria(detachedCriteria);
	}
}

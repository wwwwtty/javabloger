package org.cms.service.core.imp;


import java.util.List;

import org.cms.service.core.BaseDataService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public class BaseDataServiceImp  extends BaseServiceImp<Object> implements BaseDataService {
	
	public List<?> findEntity(Class<?> entity,List<Criterion> filters){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(entity);
		for(Criterion  r:filters){
			detachedCriteria.add(r);
		}
		return this.baseDao.findByCriteria(detachedCriteria);
	}
}

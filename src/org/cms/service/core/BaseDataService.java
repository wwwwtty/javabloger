package org.cms.service.core;

import java.util.List;

import org.cms.doamin.auth.Function;
import org.hibernate.criterion.Criterion;


public interface BaseDataService extends BaseService<Object> {
	
	public List<?> findEntity(Class<?> entity,List<Criterion> filters);
}

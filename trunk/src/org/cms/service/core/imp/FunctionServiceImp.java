package org.cms.service.core.imp;


import java.util.List;

import org.cms.doamin.auth.Function;
import org.cms.service.core.FunctionService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class FunctionServiceImp extends BaseServiceImp<Function> implements FunctionService {
	public List<Function> findByGroupCode(String code){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Function.class);
		detachedCriteria.add(Restrictions.eq("groupCode", code));
		detachedCriteria.add(Restrictions.eq("enabled", true));
		return (List<Function>) this.baseDao.findByCriteria(detachedCriteria);
	}
}

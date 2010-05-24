package org.cms.service.core.imp;


import java.util.List;

import org.cms.doamin.auth.Function;
import org.cms.service.core.FunctionService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionServiceImp extends BaseServiceImp<Function> implements FunctionService {
	
	public List<Function> findByGroupCode(String code,String valid){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Function.class);
		detachedCriteria.add(Restrictions.eq("groupCode", code));
		if("Y".equals(valid)||"N".equals(valid)){
			detachedCriteria.add(Restrictions.eq("enabled", valid));
		}else if(!"A".equals(valid)){
			log.warn("查询参数为未支持参数!");
		}
		return (List<Function>) this.baseDao.findByCriteria(detachedCriteria);
	}
}

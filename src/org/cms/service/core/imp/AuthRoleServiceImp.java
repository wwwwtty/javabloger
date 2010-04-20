package org.cms.service.core.imp;


import java.util.List;

import org.cms.doamin.auth.Role;
import org.cms.service.core.AuthRoleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class AuthRoleServiceImp extends BaseServiceImp<Role> implements AuthRoleService {
	public List<Role> findByGradeCode(String code){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Role.class);
		detachedCriteria.add(Restrictions.like("grade", code+"__"));
		detachedCriteria.add(Restrictions.eq("enabled", true));
		return (List<Role>) this.baseDao.findByCriteria(detachedCriteria);
	}
}

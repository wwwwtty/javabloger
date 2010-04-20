package org.cms.service.core;

import java.util.List;

import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;


public interface AuthRoleService extends BaseService<Role> {
	public List<Role> findByGradeCode(String code);
}

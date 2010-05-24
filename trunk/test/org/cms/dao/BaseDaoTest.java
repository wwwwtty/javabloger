package org.cms.dao;

import org.cms.BaseTestCase;
import org.cms.doamin.auth.Role;

public class BaseDaoTest extends BaseTestCase {

	public void testSaveRole(){
		Role role=new Role();
		role.setRoleName("测试数据");
		role.setRoleCode("testets");
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		baseDao.save(role);
	}
}

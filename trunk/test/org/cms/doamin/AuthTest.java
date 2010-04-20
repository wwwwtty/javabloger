package org.cms.doamin;

import org.cms.BaseTestCase;
import org.cms.dao.BaseDAO;
import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.doamin.auth.User;


public class AuthTest extends  BaseTestCase{

	
	public void testAddUser(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		
		User user=new User();
		user.setRealName("何胜超");
		user.setPassword("wsketgfhv");
		user.setSex(true);
		user.setShowName("超超");
		user.setUsername("heshencao");
		baseDao.save(user);
	}
	
	public void testAddRole(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		
		Role role=new Role();
		role.setRoleName("文章管理");
		role.setRoleDesc("管理系统里的文章栏目与编写");
		role.setRoleCode("ROLE_CONTENT_MANAGER");
		baseDao.save(role);
	}
	
	
	public void testAddFunction(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		
		Function fun=new Function();
		fun.setCode("index");
		fun.setName("首页");
		fun.setInfo("首页");
		fun.setUrl("/index.jsp");
		baseDao.save(fun);
	}
}

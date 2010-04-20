package org.cms.service;

import java.util.List;

import org.cms.BaseTestCase;
import org.cms.doamin.auth.Role;
import org.cms.service.authorize.AuthManager;

public class AuthManagerTest extends BaseTestCase {

	public void testLoadUserByUsername(){
		AuthManager authManager=(AuthManager) this.applicationContext.getBean("authService");
		authManager.loadUserByUsername("heshencao");
	}
//	
	public void testGetRoleByUser(){
		AuthManager authManager=(AuthManager) this.applicationContext.getBean("authService");
//		AuthUser user=(AuthUser) authManager.loadUserByUsername("heshencao");
		List<Role> rs=authManager.getRoleByUser("402880612705443c0127054441d70001");
		System.out.print(rs);
	}
}

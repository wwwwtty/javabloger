package org.cms.web.vo;

import org.cms.doamin.auth.Role;

public class RoleVo extends Role {
	private static final long serialVersionUID = 1L;
	//中间变量,不作任何用,防止Jons_Lib报错;
	private String parentRoleCode;
	
	
	public void setParentRoleCode(String parentRoleCode) {
		super.setParentRoleCode(parentRoleCode);
		this.parentRoleCode=parentRoleCode;
	}
}

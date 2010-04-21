package org.cms.service.core.imp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cms.core.SiteUtils;
import org.cms.dao.impl.PramasMap;
import org.cms.doamin.auth.Role;
import org.cms.service.core.AuthRoleService;

public class AuthRoleServiceImp extends BaseServiceImp<Role> implements AuthRoleService {

	public List<Role> findByParentCode(String code){
		if(StringUtils.isEmpty(code)||"root".equals(code)){
			String sql="SELECT  R.*  AS grade FROM auth_role R WHERE "
				+"(CHAR_LENGTH(R.role_code)-CHAR_LENGTH(REPLACE(R.role_code,'&','')))/(CHAR_LENGTH('&'))=0";
			return (List<Role>) this.baseDao.findBySql(sql,Role.class);
		}else{
			String sql="SELECT  R.*  AS grade FROM auth_role R WHERE "
				+"(CHAR_LENGTH(R.role_code)-CHAR_LENGTH(REPLACE(R.role_code,'&','')))/(CHAR_LENGTH('&'))=:grade "
				+" and R.role_code like :code";
			int grade=StringUtils.countMatches(code, SiteUtils.SEPARATOR_CONTACT)+1;
			PramasMap map=new PramasMap();
			map.put("code", code+"&%");
			map.put("grade", grade);
			map.setEntity(Role.class);
			return (List<Role>) this.baseDao.findBySql(sql,map);
		}
	}
}

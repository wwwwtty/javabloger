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

	public List<Role> findByParentCode(String code,String valid){
		if(StringUtils.isEmpty(code)||"root".equals(code)){
			String sql="SELECT  R.*  AS grade FROM auth_role R WHERE "
				+"(CHAR_LENGTH(R.role_code)-CHAR_LENGTH(REPLACE(R.role_code,'&','')))/(CHAR_LENGTH('&'))=0 ";
			if("Y".equals(valid)||"N".equals(valid)){
				sql+=" and enabled='"+valid+"'";
			}else if(!"A".equals(valid)){
				log.warn("查询参数为未支持参数!");
			}
			log.warn(sql);
			return (List<Role>) this.baseDao.findBySql(sql,Role.class);
		}else{
			String sql="SELECT  R.*  AS grade FROM auth_role R WHERE "
				+"(CHAR_LENGTH(R.role_code)-CHAR_LENGTH(REPLACE(R.role_code,'&','')))/(CHAR_LENGTH('&'))=:grade "
				+" and R.role_code like :code";
			if("Y".equals(valid)||"N".equals(valid)){
				sql+=" and enabled='"+valid+"' ";
			}else if(!"A".equals(valid)){
				log.warn("查询参数为未支持参数!");
			}
			int grade=StringUtils.countMatches(code, SiteUtils.SEPARATOR_CONTACT)+1;
			PramasMap map=new PramasMap();
			map.put("code", code+"&%");
			map.put("grade", grade);
			map.setEntity(Role.class);
			return (List<Role>) this.baseDao.findBySql(sql,map);
		}
	}
}

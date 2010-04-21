package org.cms.service.core;

import java.util.List;

import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;


public interface AuthRoleService extends BaseService<Role> {
	
	/**依据你节点Code查询对应的Role队列;
	 * 当Code为空或者为"root"时得到根目录(最大角色对象,角色树的顶点)
	 * @param code 
	 * @return
	 */
	public List<Role> findByParentCode(String code);
}

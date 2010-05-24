package org.cms.service.core;

import java.util.List;

import org.cms.doamin.auth.Function;


public interface FunctionService extends BaseService<Function> {
	/**通过Code查询功能,
	 * @param code
	 * @param valid 查询类型 true:全部有效数据,false:无效数据,all:全部数据;
	 * @return
	 */
	public List<Function> findByGroupCode(String code,String valid);
}

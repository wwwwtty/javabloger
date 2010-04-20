package org.cms.service.core;

import java.util.List;

import org.cms.doamin.auth.Function;


public interface FunctionService extends BaseService<Function> {
	public List<Function> findByGroupCode(String code);
}

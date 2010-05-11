package org.cms.web;

import org.cms.core.JSONError;
import org.cms.core.JSONResponse;
import org.hibernate.mapping.Constraint;
import org.hibernate.validator.Validator;



public class JSONController {
	
	public JSONResponse successed(Object obj) {
		return JSONResponse.sucess(obj);
	}
	
	public JSONResponse failed(Throwable e) {
		return JSONResponse.failed(new JSONError(e));
	}
}

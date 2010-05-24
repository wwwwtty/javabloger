package org.cms.service;

import java.util.List;

import org.cms.BaseTestCase;
import org.cms.doamin.auth.Function;
import org.cms.service.core.FunctionService;

public class FunctionServiceTest extends BaseTestCase {

	public void testGetAll(){
		System.out.println("test...");
		FunctionService fs=(FunctionService) this.applicationContext.getBean("functionService");
		List<Function> list=fs.findAll(fs.FIND_ALL_VALID);
		
		System.out.println("code:"+list.get(0).getCode());
		System.out.println("id:"+list.get(0).getId());
	}
	
	public void testSave(){
		System.out.println("test...");
		FunctionService fs=(FunctionService) this.applicationContext.getBean("functionService");
		
		Function fun=new Function();
		
		fun.setName("文件管理");
		fun.setCode("fileManager");
		fun.setUrl("/mamanger/file/control.jsp");
		System.out.println("run save!");
		fs.save(fun);
		System.out.println("save sucess!");
	}
}

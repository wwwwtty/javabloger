package org.cms.service;

import java.util.List;

import org.cms.BaseTestCase;
import org.cms.core.utils.SpringContextUtil;
import org.cms.doamin.auth.Function;
import org.cms.service.core.FunctionService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class FunctionServiceTest extends BaseTestCase {

	@Test
	public void testGetAll(){
		ApplicationContext context = SpringContextUtil.getApplicationContext();
		FunctionService fs=(FunctionService) this.applicationContext.getBean("functionService");
		List<Function> list=fs.findValid();
		
		System.out.println("code:"+list.get(0).getCode());
		System.out.println("id:"+list.get(0).getId());
	}
	
	
	@Test
	public void testSave(){
		ApplicationContext context = SpringContextUtil.getApplicationContext();
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

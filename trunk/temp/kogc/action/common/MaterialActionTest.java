package com.kogc.action.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

import com.kogc.action.material.MaterialAction;

/*
 *@auther heshencao 
 * 2009-9-8 上午10:49:41
 */
@ContextConfiguration(locations=
		{"/applicationContext.xml",
		"/applicationContext-service.xml",
		"/applicationContext-dao.xml",
		"/applicationContext-security.xml",
		"/applicationContext-bodoc.xml",
		"/applicationContext-aop.xml"})
public class MaterialActionTest extends AbstractTransactionalJUnit38SpringContextTests {

	
//	@Autowired
//	MaterialAction materialAction;
//	
	@Test
	public void testGetMaterialData() throws IOException{		
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setParameter("sapcode", "7.49950210131703");
		
		MaterialAction materialAction=new MaterialAction();
		materialAction.getMaterialData(null, null, request, response);
//		OutputStream out=response.getOutputStream();
		System.out.println(response.getContentAsString());
		
	}

	
}

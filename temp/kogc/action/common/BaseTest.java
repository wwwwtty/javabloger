package com.kogc.action.common;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;
/*
 *@auther heshencao 
 * 2009-9-15 下午12:21:58
 */
@ContextConfiguration(locations=
{"/applicationContext.xml",
"/applicationContext-service.xml",
"/applicationContext-dao.xml",
"/applicationContext-security.xml",
"/applicationContext-bodoc.xml",
"/applicationContext-aop.xml"})
public class BaseTest extends AbstractTransactionalJUnit38SpringContextTests{

	
	MockHttpServletResponse response = new MockHttpServletResponse();
	MockHttpServletRequest request = new MockHttpServletRequest();
}

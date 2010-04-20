package org.cms.core.utils;

import org.cms.BaseTestCase;
import org.springframework.context.ApplicationContext;

public class SpringUtilsTest extends BaseTestCase {
	@SuppressWarnings("unused")
	public void testGetApplication(){
		
		ApplicationContext context=SpringContextUtil.getApplicationContext();
		assertNotNull(context);
	}
}

package org.cms;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;



@ContextConfiguration(locations={"classpath*:conf/app*.xml"})
//public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
public class BaseTestCase extends AbstractJUnit38SpringContextTests {
	
	
	protected Logger log=LoggerFactory.getLogger(getClass());
	
	public String toJson(Object obj){
		JSON json=JSONSerializer.toJSON(obj);
		return json.toString();
	}
	
//	public void test_1(){
//		log.info("test....");
//	}
}

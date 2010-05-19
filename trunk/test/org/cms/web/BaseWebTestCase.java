package  org.cms.web;

import org.cms.BaseTestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations={"classpath*:view/spring_web_control.xml"})
public class BaseWebTestCase extends BaseTestCase {
	protected MockHttpServletRequest request=new MockHttpServletRequest();
	protected MockHttpServletResponse response=new MockHttpServletResponse();
	protected MockHttpSession session=new MockHttpSession();
}

package  org.cms.web;

import org.cms.core.JSONResponse;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author heshencao
 * 权限认证测试
 */
public class AuthDataServierTest extends BaseWebTestCase {

	public void testFindFunctions(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		JSONResponse view=control.findRolesAndFunctions("",session);
		String json=view.toJson();
		log.info(json);
	}
	public void testFindFunctions_1(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		JSONResponse view=control.findRolesAndFunctions("0001",session);
		String json=view.toJson();
		log.info(json);
	}
	public void testFindFunctions_2(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		JSONResponse view=control.findRolesAndFunctions("0002",session);
		String json=view.toJson();
		log.info(json);
	}
	
	public void testFindAll_2(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		JSONResponse view=control.findAllRole("all");
		String json=view.toJson();
		log.info(json);
	}
}

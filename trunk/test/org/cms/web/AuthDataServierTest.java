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
		JSONResponse view=control.findRolesAndFunctions(this.request);
		String json=view.toJson();
		log.info(json);
	}
	public void testFindFunctions_1(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		this.request.setParameter("code", "0001");
		JSONResponse view=control.findRolesAndFunctions(request);
		String json=view.toJson();
		log.info(json);
	}
	public void testFindFunctions_2(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		this.request.setParameter("code", "0002");
		JSONResponse view=control.findRolesAndFunctions(request);
		String json=view.toJson();
		log.info(json);
	}
}

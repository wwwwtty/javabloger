package  org.cms.web;

import org.springframework.web.servlet.ModelAndView;


/**
 * @author heshencao
 * 权限认证测试
 */
public class AuthDataServierTest extends BaseWebTestCase {

	public void testFindFunctions(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		ModelAndView view=control.findFunctions(this.request);
		String json=toJson(view.getModelMap());
		log.info(json);
	}
	public void testFindFunctions_1(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		this.request.setParameter("code", "0001");
		ModelAndView view=control.findFunctions(request);
		String json=toJson(view.getModelMap());
		log.info(json);
	}
	public void testFindFunctions_2(){
		AuthDataControl control=this.applicationContext.getBean(AuthDataControl.class);
		this.request.setParameter("code", "0002");
		ModelAndView view=control.findFunctions(request);
		String json=toJson(view.getModelMap());
		log.info(json);
	}
}

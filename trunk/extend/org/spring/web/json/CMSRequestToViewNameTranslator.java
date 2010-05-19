package org.spring.web.json;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.RequestToViewNameTranslator;

/**自动为没有指定View名称的ModelAndView对象指定名称; 如:
 * </br>
 * <code>
 * ModelAndView view=new ModelAndView()
 * view.add(....);
 * return view;
 * </code>
 * <br>
 *  通过在Xml文件中配置此Bean对象.
 *  DispatcherServlet会在遇到上属ModelAndView对象时自动为其加载上View名称;
 * @author heshencao
 *
 */
public class CMSRequestToViewNameTranslator implements RequestToViewNameTranslator {

	/* 得到默认的View名称;
	 * @see org.springframework.web.servlet.RequestToViewNameTranslator#getViewName(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getViewName(HttpServletRequest request) throws Exception {
	
		String uri=request.getRequestURI();
		int si=uri.indexOf(".")+1;
		int ei=uri.indexOf("?");
		ei=ei>0?ei:uri.length();
		
		String type=uri.substring(si,ei);
		
		
		String view=null;
		if("do".equals(type)){
			view= "jsonView";
		}
		else if("action".equals(type)){
			view=  "";
		}
		else{
			view= type;
		}
		System.out.println("CMSRequestToViewNameTranslator,:view:"+view);
		return view;
	}
}

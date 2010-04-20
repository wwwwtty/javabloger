package org.cms.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class WebUtils {
	public static String Data_alias="value";
	public static ModelAndView buildReturnView(Object obj){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(Data_alias, obj);
		ModelAndView view=new ModelAndView("jsonView",map); 
		return view;
	}
	
	public static void WriteJson(HttpServletResponse response,String json) throws IOException{
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json);
	}
}

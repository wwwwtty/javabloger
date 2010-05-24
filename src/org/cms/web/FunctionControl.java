package org.cms.web;

import org.cms.core.utils.WebUtils;
import org.cms.doamin.auth.Function;
import org.cms.service.core.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manager/auth/FunctionService.do")
public class FunctionControl {

	@Autowired
	private FunctionService fs;
	
	@RequestMapping(params = "action=getById") 
	public ModelAndView getById(String id){
		Function fun=fs.findById(id);
		return WebUtils.buildReturnView(fun);
	}
	
	@RequestMapping(params = "action=getAll") 
	public ModelAndView findAll(){
		return WebUtils.buildReturnView(fs.findAll());
	}
}

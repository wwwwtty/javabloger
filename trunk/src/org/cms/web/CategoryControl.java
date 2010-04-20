package org.cms.web;

import java.util.HashMap;
import java.util.Map;

import org.cms.core.utils.WebUtils;
import org.cms.doamin.cms.Category;
import org.cms.service.core.CategoryService;
import org.cms.web.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/categoryService.do")
public class CategoryControl {
	
	@Autowired
	private CategoryService cs;
	
	@RequestMapping(params = "action=add") 
	public String addCategory(CategoryVo cv){
		cs.save(cv);
		return null;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=getById") 
	public ModelAndView getCategoryById(String id){
		Category ca=cs.findById(id);
		return WebUtils.buildReturnView(ca);
	}
}

package org.cms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cms.core.Ext.TreeNode;
import org.cms.core.utils.WebUtils;
import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.service.core.AuthRoleService;
import org.cms.service.core.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manager/auth/AuthDataService.do")
public class AuthDataControl{

	@Autowired
	private FunctionService fs;
	@Autowired
	private AuthRoleService rs;
	
	@RequestMapping(params = "action=getById") 
	public ModelAndView getById(String id){
		Function fun=fs.findById(id);
		return WebUtils.buildReturnView(fun);
	}
	
	@RequestMapping(params = "action=getAll") 
	public ModelAndView findAll(){
		return WebUtils.buildReturnView(fs.findValid());
	}
	
	/**获得权限分组;用于页面左边Ext树展现;
	 * @param code
	 * @return
	 */
	@RequestMapping(params = "action=findFunctions") 
	public ModelAndView findFunctions(HttpServletRequest request){
		String code=request.getParameter("code");
		List<TreeNode> tree=new ArrayList<TreeNode>();
		List<Role> roles=rs.findByParentCode(code);
		//获得角色;
		if(roles!=null&&roles.size()>0){
			for(Role r: roles){
				TreeNode node=new TreeNode(r.getRoleCode(),r.getRoleName(),false);
				node.addAttribute("entity",r);
				tree.add(node);
			}
			return WebUtils.buildReturnView(tree);
		}
		else{//获得对应角色下面的功能点!
			List<Function> funs=fs.findByGroupCode(code);
			if(funs!=null){
				for(Function f: funs){
					TreeNode node=new TreeNode(f.getCode(),f.getName(),true);
					node.addAttribute("url", f.getUrl());
					node.addAttribute("entity",f);
					tree.add(node);
				}
			}
			return WebUtils.buildReturnView(tree);
		}
	}
	
	/**获得权限分组;用于页面左边Ext树展现;
	 * @param code
	 * @return
	 */
	@RequestMapping(params = "action=findAllRole") 
	public ModelAndView findAllRole(){
		List<TreeNode> tree=new ArrayList<TreeNode>();
		List<Role> roles=rs.findValid();
		//获得角色;
		if(roles!=null&&roles.size()>0){
			for(Role r: roles){
				tree.add(new TreeNode(r.getRoleCode(),r.getRoleName(),false));
			}
		}
		return WebUtils.buildReturnView(tree);
	}
}

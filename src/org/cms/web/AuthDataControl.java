package org.cms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.cms.core.JSONResponse;
import org.cms.core.Ext.ComboxRecord;
import org.cms.core.Ext.TreeNode;
import org.cms.core.utils.JsonUtils;
import org.cms.core.utils.SessionUtils;
import org.cms.core.utils.WebUtils;
import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.service.core.AuthRoleService;
import org.cms.service.core.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	@ModelAttribute("json")
	public ModelAndView getById(String id){
		Function fun=fs.findById(id);
		return WebUtils.buildReturnView(fun);
	}
	
	@RequestMapping(params = "action=getAll") 
	@ModelAttribute("json")
	public ModelAndView findAll(){
		return WebUtils.buildReturnView(fs.findValid());
	}
	
	/** 获得权限分组;用于页面左边Ext树展现;
	 * 当为超级用户("admin")时,返回所有权限与分组;
	 * @param code
	 * @return
	 */
	@RequestMapping(params = "action=findFunctions") 
	@ModelAttribute("json")
	public JSONResponse findRolesAndFunctions(HttpServletRequest request){
		String code=request.getParameter("code");
		
		
		String userId=SessionUtils.getUserID(request.getSession());
		if("admin".equals(userId)){
			
		}
		//获得角色;
		List<Role> roles=rs.findByParentCode(code);
		if(roles!=null&&roles.size()>0){
			List<TreeNode> tree=new ArrayList<TreeNode>();
			for(Role r: roles){
				TreeNode node=new TreeNode(r.getRoleCode(),r.getRoleName(),false);
				node.addAttribute("entity",r);
				tree.add(node);
			}
			//return WebUtils.buildReturnView(tree);
			return JSONResponse.sucess(tree);
		}
		else{//获得对应角色下面的功能点!
			List<Function> funs=fs.findByGroupCode(code);
			List<TreeNode> tree=new ArrayList<TreeNode>();
			if(funs!=null){
				for(Function f: funs){
					TreeNode node=new TreeNode(f.getCode(),f.getName(),true);
					node.addAttribute("url", f.getUrl());
					node.addAttribute("entity",f);
					tree.add(node);
				}
			}
			return JSONResponse.sucess(tree);
		}
	}
	
	/**获得权限分组;用于页面左边Ext树展现;
	 * @param code
	 * @return
	 */
	@RequestMapping(params = "action=findAllRole") 
	@ModelAttribute("json")
	public JSONResponse findAllRole(){
		List<ComboxRecord> tree=new ArrayList<ComboxRecord>();
		List<Role> roles=rs.findValid();
		//获得角色;
		if(roles!=null&&roles.size()>0){
			for(Role r: roles){
				tree.add(new ComboxRecord(r.getRoleName(),r.getRoleName(),r.getRoleCode()));
			}
		}
		return JSONResponse.sucess(tree);
	}
	/**保存功能点信息;
	 * @return
	 */
	@RequestMapping(params = "action=saveFunction") 
	@ModelAttribute("json")
	public JSONResponse saveFunction(){
		List<ComboxRecord> tree=new ArrayList<ComboxRecord>();
		List<Role> roles=rs.findValid();
		//获得角色;
		if(roles!=null&&roles.size()>0){
			for(Role r: roles){
				tree.add(new ComboxRecord(r.getRoleName(),r.getRoleName(),r.getRoleCode()));
			}
		}
		return JSONResponse.sucess(tree);
	}
	
	/**保存角色信息;
	 * @return
	 */
	@RequestMapping(params = "action=saveRole") 
	@ModelAttribute("json")
	public JSONResponse saveRole(@RequestParam(value="json") String json){
		
		Role role=(Role) JsonUtils.toObject(json, Role.class);
		//获得角色;
		rs.save(role);
		return JSONResponse.sucess("添加成功");
	}
}

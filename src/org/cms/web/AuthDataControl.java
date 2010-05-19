package org.cms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.cms.core.JSONResponse;
import org.cms.core.Ext.ComboxRecord;
import org.cms.core.Ext.TreeNode;
import org.cms.core.exception.ActionException;
import org.cms.core.utils.ExtBuilder;
import org.cms.core.utils.JsonUtils;
import org.cms.core.utils.SessionUtils;
import org.cms.core.utils.WebUtils;
import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.service.core.AuthRoleService;
import org.cms.service.core.FunctionService;
import org.cms.web.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/manager/auth/AuthDataService.do")
public class AuthDataControl{
private Logger log=LoggerFactory.getLogger(AuthDataControl.class);
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
	public JSONResponse findRolesAndFunctions(@RequestParam(value="code",required=false,defaultValue="root") String code,HttpSession session){
		
		String userId=SessionUtils.getUserID(session);
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
	 * @param isValide查询类型,
	 * 	valid:查询全部有效数据;
	 * 	invalid 查询全部无效数据;
	 * 	all:查询全部数据;默认为此参数;
	 * @return
	 */
	@RequestMapping(params = "action=findAllRole") 
	@ModelAttribute("json")
	public JSONResponse findAllRole(@RequestParam(value="isValide",required=false,defaultValue="all") String isValide){
		List<TreeNode> tree=new ArrayList<TreeNode>();
		List<Role> roles=null;
		//获得角色;
		if("valid".equals(isValide)){
			roles=rs.findValid();
		}else if("invalid".equals(isValide)){
			
		}else if("all".equals(isValide)) {
			roles=rs.findAll();
		}else{
			throw new ActionException("查询参数出错...");
		}
		//组装成TreeNode形式;
		if(roles!=null&&roles.size()>0){
			for(Role r: roles){
				tree.add(new TreeNode(r.getRoleCode(),r.getRoleName(),false,r.getParentRoleCode()));
			}
		}
		tree=ExtBuilder.buildTreeList(tree);
		return JSONResponse.sucess(tree);
	}
	/**保存功能点信息;
	 * @return
	 */
	@RequestMapping(params = "action=saveFunction") 
	@ModelAttribute("json")
	public JSONResponse saveFunction(@RequestParam(value="json",required=true)String json){
		try{
			Function fun=(Function) JsonUtils.toObject(json, Function.class);
		
		String id=fs.save(fun);
		return JSONResponse.sucess("功能点添加成功",id);
		}catch(Exception e){
			log.error("功能点添加出错",e);
			return JSONResponse.failed("功能点添加出错");
		}
	}
	
	/**保存角色信息;
	 * @return
	 */
	@RequestMapping(params = "action=saveRole") 
	@ModelAttribute("json")
	public JSONResponse saveRole(@RequestParam(value="json") String json){
		try{
			Role role=(Role) JsonUtils.toObject(json, Role.class);
			//获得角色;
			String id=rs.save(role);
			return JSONResponse.sucess("添加成功",id);
		}catch(Exception e){
			log.error("添加失败",e);
			return JSONResponse.failed("添加 / 修改失败");
		}
	}
}

package org.cms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.cms.core.utils.WebUtils;
import org.cms.service.core.BaseDataService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/manager/BaseDataService.do")
public class BasicDataControl{

	
	@Resource(type=BaseDataService.class)
	private BaseDataService service;
	
	/**获得指定实体;
	 * 指定上传参数:entity,columns,filters
	 * entity指定的连接符:
	 * 	: 表示 等于(=)
	 *  ! 表示 不等于(!=)
	 * 	> 表示 大于(>)
	 * 	< 表示 小于 (<)
	 *  ; 表示 条件分隔符
	 *  columns指定字段名称替换符;
	 *  : 换取另一个别名;
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "action=findEntity") 
	public void findEntity(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//组装实体类名;
		String className =request.getParameter("entity");
		String columns=request.getParameter("columns");
		String filter=request.getParameter("filter");
//		if(!className.contains(".")){
//			className = "com.kogc.common.po.meta." + entity;
//		}
		
		List<Criterion> rs=new ArrayList<Criterion>();
		Class<?> clazz=Class.forName(className);
		//组装关系集
		String[] filters=filter.split(";");
		for(String f:filters){
			if(f.contains(":")&&f.lastIndexOf(":")!=f.length()){
				String[] ss=f.split(":");
				rs.add(Restrictions.eq(ss[0], ss[1]));
			}
			else if(f.contains(">")&&f.lastIndexOf(">")!=f.length()){
				String[] ss=f.split(">");
				rs.add(Restrictions.gt(ss[0], ss[1]));
			}
			else if(f.contains("<")&&f.lastIndexOf("<")!=f.length()){
				String[] ss=f.split("<");
				rs.add(Restrictions.lt(ss[0], ss[1]));
			}
			else if(f.contains("!")&&f.lastIndexOf("!")!=f.length()){
				String[] ss=f.split("!");
				rs.add(Restrictions.not(Restrictions.eq(ss[0], ss[1])));
			}
		}
		//查询结果;
		List<?> list=service.findEntity(clazz,rs);
		
		//对展现数据包装;
		final List<String> colList = new ArrayList<String>();
		Map<String,String> alias=new HashMap<String,String>();//替换名称集;
		if(!StringUtils.isEmpty(columns)){
			for(String s:columns.split(";")){
				if(s.contains(":")){
					String[] ss=s.split(":");
					colList.add(ss[0]);
					alias.put(ss[0], ss[1]);
				}else{
					colList.add(s);
				}
			}
		}
		//过滤请求的展现字段;如果未指定,则全部展现;
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				return !(colList.size()==0||colList.contains(name));
			}
		});
		//生成Json字符集;
		String result = JSONArray.fromObject(list, config).toString();
		
		//对名称进行替换;
		Set<String> ks=alias.keySet();
		for(String k:ks){
			String nn=alias.get(k);//newName;
			result=result.replace("\""+k+"\"", "\""+nn+"\"");
		}
		throw new Exception("sdafd");
		//WebUtils.WriteJson(response, result);
	}
}

package org.cms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cms.core.JSONResponse;
import org.cms.core.Ext.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/testData.do")
public class TestDataAction extends JSONController{
	private Map data=null;
	
	@RequestMapping(params = "action=getDataById") 
	@ModelAttribute("json")
	public JSONResponse getDataById(@RequestParam(required=false,value="node") String id){
		if(null==id||StringUtils.isEmpty(id)){
			id="root";
		}
		this.initData();
		List list=new ArrayList();
		list.add(this.data.get(id));
		
		TreeNode node=new TreeNode();
		node.setId("testtest");
		node.setText("测试");
		list.add(node);
		
		return JSONResponse.sucess(list);
	}
	
	private void initData(){
		this.data=new HashMap();
		TreeNode node=new TreeNode();
		node.setId("cao");
		node.setText("超");
		data.put("root", node);
		
		
		
		node=new TreeNode();
		node.setId("caocao");
		node.setText("超超");
		data.put("cao", node);
		
		node=new TreeNode();
		node.setId("caocaocao");
		node.setText("超超超");
		data.put("caocao", node);
		
		node=new TreeNode();
		node.setId("caocaocaocao");
		node.setText("超超超");
		data.put("caocaocao", node);
	}
}

package org.cms.core.Ext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cms.doamin.auth.Role;

public class TreeNode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String text ;
	private boolean leaf ;
	/**
	 * 扩展属性;方便扩展;
	 */
	private Map<String,Object> attributs;
	public TreeNode(){
		
	}
	public TreeNode(String id, String text, boolean leaf) {
		super();
		this.id = id;
		this.text  = text;
		this.leaf  = leaf;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text ;
	}
	public void setText(String text) {
		this.text  = text;
	}
	public boolean isLeaf () {
		return leaf ;
	}
	public void setLeaf(boolean left) {
		this.leaf  = left;
	}
	public void setAttributs(Map<String, Object> attributs) {
		this.attributs = attributs;
	}
	public Map<String, Object> getAttributs() {
		return attributs;
	}

	/**快捷添加属性;
	 * @param k
	 * @param v
	 */
	public void addAttribute(String k,Object v) {
		if(this.attributs==null){
			this.attributs=new HashMap<String, Object>();
		}
		this.attributs.put(k, v);
	}
}

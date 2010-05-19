package org.cms.core.Ext;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String text ;
	private boolean leaf ;
	/**父节点ID;方便ExtBuilder构建父子结构;*/
	private String parentNodeId;
	/**扩展属性;方便扩展;*/
	private Map<String,Object> attributs;
	/** 子项, */
	private List<TreeNode> childNodes;
	public TreeNode(){
		
	}
	/**
	 * @param id
	 * @param text
	 * @param leaf
	 * @param parentId
	 */
	public TreeNode(String id, String text, boolean leaf,String parentId) {
		super();
		this.id = id;
		this.text  = text;
		this.leaf  = leaf;
		this.parentNodeId=parentId;
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
	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getParentNodeId() {
		return parentNodeId;
	}
	public void setChildNodes(List<TreeNode> childNodes) {
		this.childNodes = childNodes;
	}
	public void addChildNodes(TreeNode node) {
		if(this.childNodes==null){
			this.childNodes=new ArrayList<TreeNode>();
		}
		this.childNodes .add(node);
	}
	public List<TreeNode> getChildNodes() {
		return childNodes;
	}
}

package org.cms.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cms.core.Ext.TreeNode;

/**各种Ext数据构建器;
 * @author heshencao
 *
 */
public class ExtBuilder {
	public static List<TreeNode> buildTreeList(List<TreeNode> list){
		Map<String,TreeNode> map=new HashMap<String,TreeNode>();
		List<TreeNode> rs=new ArrayList<TreeNode>();
		for(TreeNode tn : list){
			map.put(tn.getId(), tn);
		}
		for(TreeNode tn : list){
			TreeNode pa=map.get(tn.getParentNodeId());
			if(pa!=null){
			//	list.remove(tn);
				pa.addChildNodes(tn);
			}else{
				rs.add(tn);
			}
		}	
		
		return rs;
	}
}

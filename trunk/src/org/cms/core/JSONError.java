package org.cms.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**针对Json转换的出错路径;
 * @author heshencao
 *
 */
public class JSONError {
	private String msg;
	private List<Map> trance;
	private JSONError cause;
	public JSONError(){
	}
	public JSONError(Throwable err){
		this.msg=err.getLocalizedMessage();
		StackTraceElement[] trs=err.getStackTrace();
		for(StackTraceElement tr: trs){
			Map<String,String> map=new HashMap<String,String>();
			map.put("line", String.valueOf(tr.getLineNumber()));
			map.put("method", tr.getMethodName());
			map.put("class", tr.getClassName());
			this.trance.add(map);
		}
		this.cause=new JSONError(err.getCause());
	}
	public String getMsg() {
		return msg;
	}
	public List<Map> getTrance() {
		return trance;
	}
}

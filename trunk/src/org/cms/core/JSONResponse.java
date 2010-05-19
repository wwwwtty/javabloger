package org.cms.core;

import java.io.Serializable;
import java.util.Map;
import net.sf.json.JSONObject;

/**服务端返回数据展现形式的封装;
 * @author heshencao
 */
public class JSONResponse {

	private String msg;
	private boolean success;
	/**
	 *服务器端返回的数据;可以是一个Object对象 ,亦可是List对象;
	 */
	private Object obj;
	private Map<String,? extends Serializable> attributes;
	public static JSONResponse sucess(String msg){
		JSONResponse jmsg=new JSONResponse();
		jmsg.msg=msg;
		jmsg.success=true;
		return jmsg;
	}
	public static JSONResponse sucess(String msg,Object obj){
		JSONResponse jmsg=new JSONResponse();
		jmsg.msg=msg;
		jmsg.obj=obj;
		jmsg.success=true;
		return jmsg;
	}
	public static JSONResponse sucess(Object obj){
		JSONResponse jmsg=new JSONResponse();
		jmsg.setObj(obj);
		jmsg.success=true;
		return jmsg;
	}
	public static JSONResponse   failed(String msg){
		JSONResponse jmsg=new JSONResponse();
		jmsg.msg=msg;
		jmsg.success=false;
		return jmsg;
	}
	public static JSONResponse   failed(JSONError e){
		JSONResponse jmsg=new JSONResponse();
		jmsg.msg=e.getMsg();
		jmsg.success=false;
		jmsg.obj=e;
		return jmsg;
	}
	
	public String toJson(){
		return JSONObject.fromObject(this).toString();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean sucess) {
		this.success = sucess;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object getObj() {
		return obj;
	}
}

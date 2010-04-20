package org.cms.core;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JsonMSG {

	private String msg;
	private boolean success;
	private List list;
	private Object obj;
	
	public static JsonMSG sucess(String msg){
		JsonMSG jmsg=new JsonMSG();
		jmsg.msg=msg;
		jmsg.success=true;
		return jmsg;
	}
	public static JsonMSG sucess(Object obj){
		JsonMSG jmsg=new JsonMSG();
		jmsg.setObj(obj);
		jmsg.success=true;
		return jmsg;
	}
	public static JsonMSG   failed(String msg){
		JsonMSG jmsg=new JsonMSG();
		jmsg.msg=msg;
		jmsg.success=false;
		return jmsg;
	}
	
	public static JsonMSG   sucess(List list){
		JsonMSG jmsg=new JsonMSG();
		jmsg.list=list;
		jmsg.success=true;
		return jmsg;
	}
	
	public static JsonMSG   failed(List list){
		JsonMSG jmsg=new JsonMSG();
		jmsg.list=list;
		jmsg.success=false;
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

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object getObj() {
		return obj;
	}
}

package org.cms.core.utils;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {

	public static String toJson(Object obj){
		return toJson(null,obj);
	}
	
	/**转换为指定名称的Json;
	 * @param objName
	 * @param obj
	 * @return
	 */
	public static String toJson(String objName,Object obj){
		String str=null;
		if(obj instanceof Collection){
			str=fromCollection(obj).toString();
		}
		else{
			str=fromObj(obj).toString();
		}
		if(objName!=null)
			str= "{\""+objName+"\":"+str+"}";
		return str;
	}
	
	
	public static Object toObject(String json,Class clazz){
		JSONObject obj=fromObj(json);
		return JSONObject.toBean(obj, clazz);
	}
	
	public static Collection toCollection(String json){
		JSONArray obj=fromCollection(json);
		return JSONArray.toCollection(obj);
	}
	
	public JSONObject toJsonObj(String json){
		return JSONObject.fromObject(json);
	}
	public JSONArray toJsonArray(String json){
		return JSONArray.fromObject(json);
	}
	
	public static JSONObject fromObj(Object obj){
		return JSONObject.fromObject(obj);
	}
	
	public static JSONArray fromCollection(Object obj){
		return JSONArray.fromObject(obj);
	}
}

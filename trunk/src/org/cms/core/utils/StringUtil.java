package org.cms.core.utils;
/*
 *@auther heshencao 
 * 2010-1-17 下午08:50:44
 */
public class StringUtil {

	public static String ConvertToStr(String value){
		return value==null?"":value;
	}
	public static String ConvertToStr(Object value){
		return value!=null?ConvertToStr(value.toString()):"";
	}
}

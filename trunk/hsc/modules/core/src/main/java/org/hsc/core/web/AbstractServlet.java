package org.hsc.core.web;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hsc.core.utils.DateFormatUtils;
import org.hsc.core.web.annotation.Action;
import org.hsc.core.web.annotation.ActionHttpRequest;
import org.hsc.core.web.annotation.ActionHttpResponse;
import org.hsc.core.web.annotation.ActionHttpSession;
import org.hsc.core.web.annotation.ActionRequestPrameter;
import org.hsc.core.web.annotation.ContextPath;
import org.hsc.core.web.annotation.ParameterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;


public class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = -795968902005500426L;
	private final static Map<String,ActionMappingMeta> actionMappingMap=new HashMap<String,ActionMappingMeta>();
	/**
	 * 用于JSON处理
	 */
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private final static Logger log=LoggerFactory.getLogger(AbstractServlet.class);

	public void destroy() {
		super.destroy(); 
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("isURLData", true);
		
		this.doPost(request, response);
		
	}

	/**处理主入口 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri=request.getRequestURI();
		log.debug("得到请求路径："+uri);
		uri=uri.substring(0,uri.indexOf("."));
		
		Set<Entry<String,ActionMappingMeta>> mappingURLSet=actionMappingMap.entrySet();
		boolean isSolution=false;
		for(Entry<String,ActionMappingMeta> um: mappingURLSet){
			//匹配成功，将由Action处理。并将处理的结果返回请求端；
			if(isSolution==false && uri.endsWith(um.getKey())){
				isSolution=true;
				ActionMappingMeta meta=um.getValue();
				Object[] args=new Object[meta.params.size()];
				for(int i=0;i<args.length;i++){
					ParamsMeta pmt= meta.params.get(i);
					
					if(pmt.paramsMode==ParamsType.REQUEST_PARAMTER){
						String v=null;
						//修正在数组上传时的BUG
						if(pmt.paramsType ==ParameterType.INT_ARRAY){
							
							String[] va=request.getParameterValues(pmt.paramsName);
//							v=request.getParameter(pmt.paramsName);
//							log.debug("获取数组参数【"+pmt.paramsName+"】：array【"+va+"】，val：【"+v+"】");
							if(va!=null && va.length>0){
								v=StringUtils.join(va,",");
							}
						}else{
							//修正通过URL上传中文字符时的异常！
							if(new Boolean(true).equals(request.getAttribute("isURLData"))){
								String p=request.getParameter(pmt.paramsName);
								if(!StringUtils.isEmpty(p)){
									v=new String(p.getBytes("ISO-8859-1"),"utf8");
								}
								
							}else{
								v=request.getParameter(pmt.paramsName);
							}
						}
						args[i]=pmt.convertValue(v);
					}else if(pmt.paramsMode==ParamsType.SESSION){
						args[i]=request.getSession();
					}else if(pmt.paramsMode==ParamsType.REQUEST){
						args[i]=request;
					}else if(pmt.paramsMode==ParamsType.RESPONSE){
						args[i]=response;
					}
					
				}
				
				//从Spring容器中获取托管对象
				Object actionInstance=null;
				try {
					actionInstance = meta.actionClazz.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				
				
				if(actionInstance==null){
					throw new RuntimeException("内部异常，不能获取服务对象！");
				}
				//执行业务处理
				try {
					Object result=meta.actionMethod.invoke(actionInstance, args);
					handleResponse(request, response, result);
				}catch (InvocationTargetException e) {
					Throwable re=e.getCause();
					log.error(re.getMessage(), re);
					response.setContentType("text/x-json;charset=UTF-8");           
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("{\"exception\":true,\"msg\":\""+re.getMessage()+"\"}");
				}catch (Exception e) {
					log.error(e.getMessage(), e);
					response.setContentType("text/x-json;charset=UTF-8");           
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("{\"exception\":true,\"msg\":\""+e.getMessage()+"\"}");
				}
				
				break;
			}
		}
		if(isSolution==false){
			response.setStatus(403);// "请求非法，服务无法处理！"
			log.warn("请求无服务(Action)响应，"+uri);
		}
		
	}


	public void handleResponse(HttpServletRequest request,HttpServletResponse response, Object result)
			throws IOException {
			if(result==null){
				return;
			}else if(result instanceof RequestRedirectWapper ){
		    	 String url=((RequestRedirectWapper)result).getDispacherURL();
		    	 log.info("前往路径："+url);
		    	 
		    	 response.sendRedirect(request.getContextPath()+"/"+url);
		    	 
		     }else if(result instanceof RequestForwordWapper ){
		    	 RequestForwordWapper frt=(RequestForwordWapper)result;
		    	 Map<String,Object> attrs=frt.getAttributes();
		    	 if(!attrs.isEmpty()){
		    		 for(Entry<String, Object>  et :attrs.entrySet()){
		    			 request.setAttribute(et.getKey(), et.getValue());
		    		 }
		    	 }
		    	 
		    	 log.info("前往路径："+frt.getForword());
		    	 try {
					request.getRequestDispatcher(frt.getForword()).forward(request, response);
				} catch (Exception e) {
					log.warn("前往路径："+frt.getForword()+"异常！",e);
//					try {
//						request.getRequestDispatcher(request.getHeader("referer")).forward(request, response);
//					} catch (ServletException se) {
//						log.warn("返回请求路径："+frt.getForword()+"异常！",e);
//					}
				}
		     }else if(result instanceof String){
				String rspJson=String.valueOf(result);
				response.setContentType("text/plain;charset=UTF-8");           
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(rspJson);
				response.getWriter().close();
			}else if(result instanceof ByteArrayOutputStream){//返回文件流，以Excel格式推送
				String excelName = (String) request.getSession().getAttribute("excelName");
				response.setHeader("Content-Disposition", "filename="+ new String(excelName.getBytes("gb2312"), "iso8859-1"));
				//response.setHeader("Content-disposition", "attachment;filename=myExcel.xls");
				response.setContentType("application/vnd.ms-excel");    
				byte[] bytes=((ByteArrayOutputStream)result).toByteArray();
				response.getOutputStream().write(bytes);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}else{
				
//				 JsonConfig jsonConfig = new JsonConfig();  
//				 jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//检测转化过程之中死递归，如果存在，则直接将相关属性设置为null
//				 jsonConfig.registerJsonValueProcessor(Date.class,  
//				                new DateJsonValueProcessor());  
//				
				response.setContentType("text/x-json;charset=UTF-8");           
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(objectMapper.writeValueAsString(result));
				response.getWriter().close();
			}
	}

	/**
	 * Initialization of the servlet. <br>
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		
		String contextPath=this.getInitParameter("contextPath");
		log.info("初始化Servlet,扫描路径:"+contextPath);
		try {
			Class<?>[] classes= ReflectionsUtils.getClasses(contextPath);
			for(Class<?> clazz:classes){
				ContextPath cp_anno=(ContextPath) clazz.getAnnotation(ContextPath.class);
				if(cp_anno!=null){
					log.debug("发现Action处理实体 ："+clazz.getName());
					
					Method[] mhs=clazz.getMethods();
					for(Method mh:mhs){
						Action ac_anno=mh.getAnnotation(Action.class);
						if(ac_anno!=null){
							String path=cp_anno.value()+"/"+ac_anno.value();
							
							ActionMappingMeta meta=new ActionMappingMeta();
							meta.actionMethod=mh;
							meta.actionClazz=clazz;
							
							actionMappingMap.put(path, meta);
							log.debug("发现URL配置路径 ："+path);
							
							Annotation[][] pas= mh.getParameterAnnotations();
							Class<?>[] paramTypes=mh.getParameterTypes();
							
							for(int i=0;i<pas.length;i++){
								Annotation[] annotations = pas[i];
								for (Annotation annotation : annotations) {
									ParamsMeta pmt = new ParamsMeta();
									if (annotation instanceof ActionRequestPrameter) {
										ActionRequestPrameter ap = (ActionRequestPrameter) annotation;

										pmt.paramsMode = ParamsType.REQUEST_PARAMTER;
										pmt.paramsName = ap.value();
										if (ap.type().equals(ParameterType.NULL)) {
											if(paramTypes[i].equals(Integer.class)){
												pmt.paramsType =ParameterType.INT;
											}else if(paramTypes[i].equals(Float.class)){
												pmt.paramsType =ParameterType.FLOAT;
											}else if(paramTypes[i].equals(Date.class)){
												pmt.paramsType =ParameterType.DATE;
											}else{
												pmt.paramsType =ParameterType.STRING;
											}
										} else {
											pmt.paramsType = ap.type();
										}
										// pmt.paramsType=((ActionRequestPrameter)
										// annotation).type();
									} else if (annotation instanceof ActionHttpSession) {
										pmt.paramsMode = ParamsType.SESSION;
									} else if (annotation instanceof ActionHttpRequest) {
										pmt.paramsMode = ParamsType.REQUEST;
									} else if (annotation instanceof ActionHttpResponse) {
										pmt.paramsMode = ParamsType.RESPONSE;
									}

									meta.params.add(pmt);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		log.info("初始化Servlet完成！");
	}
	
	private class ActionMappingMeta{
		Method actionMethod;
		Class<?> actionClazz;
		List<ParamsMeta> params=new ArrayList<ParamsMeta>();
	}
	
	private class ParamsMeta{
		public ParamsType paramsMode;
		public String paramsName;
		public ParameterType paramsType=ParameterType.STRING;
		
		public Object convertValue(String value){
			
			if(paramsType==ParameterType.STRING){//默认转换为String类型
				if(!StringUtils.isEmpty(value)){////去掉空格
					value=value.trim();
				}
				return value;
			}else if(paramsType==ParameterType.INT){
				try{
					return Integer.parseInt(value);
				}catch(Exception e){
					log.warn("参数【"+paramsName+"】值【"+value+"】转换为整数时异常！");
					return null;
				}
			}else if(paramsType==ParameterType.FLOAT){
				try{
					return Float.parseFloat(value);
				}catch(Exception e){
					log.warn("参数【"+paramsName+"】值【"+value+"】转换为整数时异常！");
					return null;
				}
			}else if(paramsType==ParameterType.INT_ARRAY){
				if(StringUtils.isEmpty(value)){
					return new Integer[0];
				}
				
				String[] va=value.split(",");
				Integer[] cva=new Integer[va.length];
				for(int i=0;i<va.length;i++){
					try{
						cva[i]=Integer.parseInt(va[i]);
					}catch(Exception e){
						log.warn("参数【"+paramsName+"】值【"+value+"】转换为整数时异常！");
						cva[i]=null;
					}
				}
				return cva;
			}else if(paramsType==ParameterType.DATE){
				return DateFormatUtils.parse(value, DateFormatUtils.DATETIME_FORMAT);
			}else{
				log.warn("参数【"+paramsName+"】值【"+value+"】,转换异常，请注意检查！");
				return value;
			}
			
		}
	}
	private enum ParamsType{
		REQUEST_PARAMTER,
		REQUEST,
		RESPONSE,
		SESSION
	}
	
}

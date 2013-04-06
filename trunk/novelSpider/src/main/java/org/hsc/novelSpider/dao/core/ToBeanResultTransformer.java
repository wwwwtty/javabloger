package org.hsc.novelSpider.dao.core;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Result transformer that allows to transform a result to
 * a user specified class which will be populated via setter
 * methods or fields matching the alias names.
 * <p/>
 * <pre>
 * List resultWithAliasedBean = s.createCriteria(Enrolment.class)
 * 			.createAlias("student", "st")
 * 			.createAlias("course", "co")
 * 			.setProjection( Projections.projectionList()
 * 					.add( Projections.property("co.description"), "courseDescription" )
 * 			)
 * 			.setResultTransformer( new AliasToBeanResultTransformer(StudentDTO.class) )
 * 			.list();
 * <p/>
 *  StudentDTO dto = (StudentDTO)resultWithAliasedBean.get(0);
 * 	</pre>
 *
 * @author max
 */
public class ToBeanResultTransformer extends AliasToBeanResultTransformer {

	private static final Logger log = LoggerFactory.getLogger(ToBeanResultTransformer.class);
	// IMPL NOTE : due to the delayed population of setters (setters cached
	// 		for performance), we really cannot properly define equality for
	// 		this transformer

	private final Class<?> resultClass;
//	private boolean isInitialized;
//	private String[] aliases;
//	private Setter[] setters;
	private Map<String,Meta> cachedField=new HashMap<String,Meta>();

	public ToBeanResultTransformer(Class<?> resultClass) {
		super(resultClass);
		if ( resultClass == null ) {
			throw new IllegalArgumentException( "resultClass cannot be null" );
		}
//		isInitialized = false;
		this.resultClass = resultClass;
		
		Class<?> parentClass=resultClass.getSuperclass();
		
		init(parentClass);
		init(resultClass);
		
	}

	private void init(Class<?> clazz) {
		Field[] fds=clazz.getDeclaredFields();
		for(Field f: fds){
			if(Modifier.isStatic(f.getModifiers())){//静态变量，忽略
				continue;
			}
			Column col=f.getAnnotation(Column.class);
			
			String fdName=f.getName();
			String methodName=fdName.substring(0, 1).toUpperCase()+fdName.substring(1, fdName.length());
		//	Method setter = null;
			Meta meta=new Meta();
			meta.filedType=f.getType();
			try {
			//	Method getter=clazz.getMethod("get"+methodName);
				meta.setter = clazz.getMethod("set"+methodName, f.getType());
			} catch (NoSuchMethodException e) {
				log.warn("初始化Setter方法【"+fdName+"】异常"+e.getMessage());
			}
			if(col!=null){
				cachedField.put(col.name(), meta);
			}else{
				cachedField.put(f.getName(), meta);
			}
		}
	}


	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result =null;
		
			try {
				result= resultClass.newInstance();
			} catch (Exception e) {
				log.error("实例化Bean【"+resultClass.getName()+"】异常"+e.getMessage(),e);
				return null;
			} 
			if(aliases==null){
				return result;
			}
			for ( int i = 0; i < aliases.length; i++ ) {
				Meta meta= cachedField.get( aliases[i]);
				if ( meta != null ) {
					String v=(tuple[i]==null)?null:tuple[i].toString();
					try{
						if(meta.filedType.equals(Boolean.class)||meta.filedType.equals(boolean.class)){
							meta.setter.invoke(result,"0".equals(v)||"true".equals(v) );	
						}else if(meta.filedType.equals(Integer.class)||meta.filedType.equals(int.class)){
							meta.setter.invoke(result, v!=null?Integer.parseInt(v):null);	
						}else if(meta.filedType.equals(Float.class)||meta.filedType.equals(float.class)){
							meta.setter.invoke(result, v!=null?Float.parseFloat(v):null);	
						}else{
							meta.setter.invoke(result, tuple[i]);
						}
					}catch(Exception e){
						log.warn("类【"+resultClass.getName()+"】，属性【"+aliases[i]+"】,方法【"+meta.setter.getName()+"】注入值【"+tuple[i]+"】异常:"+e.getMessage());
					}
				}
			}
		
		return result;
	}

	public static ToBeanResultTransformer aliase(Class<?> clazz){
		return new ToBeanResultTransformer(clazz);
	}
	
	private class Meta{
		private Method setter;
		private Class<?> filedType;
//		private String fieldName;
	}
}

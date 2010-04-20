package org.cms.dao.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

public class BeanTransformer implements ResultTransformer {

	private static final long serialVersionUID = 5921971154720661738L;
	private final Class resultClass;
	private Map<String, String> parmMap = new HashMap<String, String>();
	private int length = 0;
	private Set<String>[] objSet = null;

	public BeanTransformer(Class resultClass) {
		this.resultClass = resultClass;
		if (resultClass != null) {
			
				
				Field[] fl=resultClass.getDeclaredFields();
				for(Field f : fl){
					try{
						Column column=(Column)f.getAnnotation(Column.class);
						parmMap.put( column.name(),f.getName());
					}catch(NullPointerException e){
						continue;
					}
				}
//				Set tmpSet = BeanUtils.describe(resultClass.newInstance()).keySet();
//				for (Iterator<String> iter = tmpSet.iterator(); iter.hasNext();) {
//					String key = iter.next();
//					parmMap.put(key.toLowerCase(), key);
//				}
		}
	}

	public List transformList(List collection) {
		return collection;
	}

	@SuppressWarnings("unchecked")
	private void constitResultSet(Object[] tuple) throws Exception {
		length = tuple.length;
		objSet = new Set[length];
		for (int i = 0; i < length; i++) {
			objSet[i] = new HashSet<String>();
			Set tmpSet = BeanUtils.describe(tuple[i]).keySet();
			for (Iterator<String> iter = tmpSet.iterator(); iter.hasNext();) {
				objSet[i].add(iter.next().toLowerCase());
			}
		}
	}

	private Object getObjValue(Object[] tuple, String key) throws Exception {
		Object result = null;
		for (int i = 0; i < length; i++) {
			if (objSet[i].contains(key.toLowerCase())) {
				result = PropertyUtils.getProperty(tuple[i], key);
				break;
			}
		}
		return result;
	}

	/* 关系数据库(元组，数组)中转换为我们可用的类型;
	 * @see org.hibernate.transform.ResultTransformer#transformTuple(java.lang.Object[], java.lang.String[])
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result = null;
		try {
			if (resultClass != null) {
				result = resultClass.newInstance();
				if (aliases != null) {
					int length = aliases.length;
					for (int i = 0; i < length; i++) {
						String alias = aliases[i].toLowerCase();
						if (tuple[i] == null || !parmMap.containsKey(alias) || alias.equals("rownum_") || alias.equals("rownum"))
							continue;
						BeanUtils.setProperty(result, parmMap.get(alias), tuple[i]);
					}
				} else {
					if (objSet == null) {
						constitResultSet(tuple);
					}
					for (Iterator<String> iterator = parmMap.keySet().iterator(); iterator.hasNext();) {
						String key = parmMap.get(iterator.next());
						Object value = getObjValue(tuple, key);
						if (value != null)
							BeanUtils.setProperty(result, key, value);
					}
				}
			} else {
				if (tuple != null && tuple.length == 1)
					result = tuple[0];
				else
					result = tuple;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}
		return result;
	}
}


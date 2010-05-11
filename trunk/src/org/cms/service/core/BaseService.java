package org.cms.service.core;

import java.util.List;

import org.cms.doamin.cms.Category;

public interface BaseService<T> {

	/**依据ID查询
	 * @param id
	 * @return
	 */
	public T findById(String id);
	
	/**保存对象,返回ID
	 * @param ca
	 * @return
	 */
	public String save(T t);
	
	/**查询所有
	 * @param t
	 * @return
	 */
	public List<T> findAll();
	
	public void update(T t);
	
	/**只查所有的有效数据;
	 * @return
	 */
	public List<T> findValid();
	
}

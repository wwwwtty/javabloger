package org.cms.service.core;

import java.util.List;

public interface BaseService<T> {
	
	public static String FIND_ALL="A";
	public static String FIND_ALL_VALID="Y";
	public static String FIND_ALL_INVALID="N";
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
	
//	@Transactional
	public void update(T t);
	
	/**只查所有的有效数据;
	 * @return
	 */
	public List<T> findAll(String valid);
	
}

package org.cms.doamin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cms.dao.BaseDAO;
import org.cms.dao.impl.PramasMap;
import org.cms.doamin.cms.Category;
import org.junit.Test;

import org.cms.BaseTestCase;


public class CategoryTetst extends  BaseTestCase{
	
	@Test
	public void testGetCategory(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		baseDao.findById(1, Category.class);
	}
	
	@Test
	public void testSaveCategory(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		Category ca=new Category();
		ca.setCode("third");
	//	ca.setDesc(" 第三张页面 ");
	//	ca.setName("测试三");
//		ca.setInvalid(invalid)
		baseDao.save(ca);
	}
	
	@Test
	public void testNamedQuery(){
		BaseDAO baseDao=(BaseDAO) this.applicationContext.getBean("baseDao");
		PramasMap nameParas = new PramasMap();
		nameParas.put("code", "index");
		List<Category> list=(List<Category>)baseDao.findByNamedQueryParas("testSelect", nameParas);
		System.out.println(list);
	}
}

package org.hsc.novelSpider.dao.utils;

import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(value = null)
public class HibernateUtilsTest {

	@Test
	public void testBuildSessionFactory(){
		HibernateUtil.getSessionFactory();
	}
}

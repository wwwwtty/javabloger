package org.hsc.novelSpider.dao.utils;

import org.junit.Test;

public class HibernateUtilsTest {

	@Test
	public void testBuildSessionFactory(){
		HibernateUtil.getSessionFactory();
	}
}

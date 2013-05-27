package org.hsc.novelSpider.dao;

import org.hsc.novelSpider.BaseTest;
import org.hsc.novelSpider.domain.Article;
import org.junit.Test;

public class ArticleDAOTest extends BaseTest{

	private ArticleDAO dao;
	
	@Test
	public void testSave(){
		Article atr=new Article();
		
		dao.save(atr);
		
	}
}

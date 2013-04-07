package org.hsc.novelSpider.dao;

import org.hsc.novelSpider.BaseTest;
import org.hsc.novelSpider.domain.Article;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleDAOTest extends BaseTest{

	private @Autowired ArticleDAO dao;
	
	@Test
	public void testSave(){
		Article atr=new Article();
		
		dao.save(atr);
		
	}
}

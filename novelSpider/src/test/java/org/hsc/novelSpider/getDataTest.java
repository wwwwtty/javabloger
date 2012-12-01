package org.hsc.novelSpider;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class getDataTest {
	
	private getData data=new getData();
	
	@Test
	public void testGetArticle() throws URISyntaxException, IOException{
		getData.main(null);
	}

}

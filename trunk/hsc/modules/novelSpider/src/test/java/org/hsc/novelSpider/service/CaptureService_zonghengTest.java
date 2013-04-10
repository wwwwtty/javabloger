package org.hsc.novelSpider.service;

import java.io.IOException;

import org.hsc.novelSpider.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class CaptureService_zonghengTest extends BaseTest{

	@Autowired()
	CaptureService_zongheng service;
	
	
	@Test
	@Rollback(value=false)
	public void testCapture() throws IOException{
		service.doCaptureData();
	}
}

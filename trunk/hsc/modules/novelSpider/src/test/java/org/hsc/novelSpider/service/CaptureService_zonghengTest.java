package org.hsc.novelSpider.service;

import java.io.IOException;

import org.hsc.novelSpider.BaseTest;
import org.junit.Test;

public class CaptureService_zonghengTest extends BaseTest{

	CaptureService_zongheng service;
	
	
	@Test
	public void testCapture() throws IOException{
		service.doCaptureData();
	}
}

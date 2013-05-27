package org.hsc.novelSpider.service;

import java.io.IOException;
import org.hsc.novelSpider.BaseTest;
import org.junit.Test;

public class CaptureService_17shuTest extends BaseTest{

	CaptureService_17shu service;
	
	
	public void testCapture() throws IOException{
		service.doCaptureData();
	}
}

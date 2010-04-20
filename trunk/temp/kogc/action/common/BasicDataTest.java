package com.kogc.action.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;


/*
 *@auther heshencao 
 * 2009-9-15 下午12:15:21
 */
public class BasicDataTest extends BaseTest{

	private BasicData basicData;
		
	@Override
	public void setUp(){
		try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		basicData=new BasicData();
	}
	
	public void testGetCarrierByCom() throws Exception{
		basicData.getCarrierByCom(null, null, request, response);
		System.out.println(response.getContentAsString());
	}
}

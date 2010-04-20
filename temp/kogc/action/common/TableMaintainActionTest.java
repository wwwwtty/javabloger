/*
 *@auther heshencao 
 * Sep 1, 2009 9:24:23 AM
 */

package com.kogc.action.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.AbstractModelAndViewTests;

public class TableMaintainActionTest extends AbstractModelAndViewTests{

	
	public void testGetTableByColumn(){		
		ServletContext context=new MockServletContext();
		HttpServletResponse response = new MockHttpServletResponse();
		HttpServletRequest request = new MockHttpServletRequest();
		
		
	}
}

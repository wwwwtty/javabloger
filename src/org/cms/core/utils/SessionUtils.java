package org.cms.core.utils;

import javax.servlet.http.HttpSession;

public class SessionUtils {
	private static String USERID="userID";

	public static void setUserID(HttpSession session,String userID){
		session.setAttribute(USERID, userID);
	}
	
	public static String getUserID(HttpSession session){
		return (String) session.getAttribute(USERID);
	}
	
	public static void removeUserID(HttpSession session){
		session.removeAttribute(USERID);
	}
}

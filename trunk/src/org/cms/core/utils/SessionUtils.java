package org.cms.core.utils;

import javax.servlet.http.HttpSession;

import org.cms.doamin.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**Session集中管理类;
 * @author heshencao
 *
 */
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
	 /**
	  * SpringSecurity权限对象管事;
	 * @return
	 */
	public static User getUser() {

		    if ((SecurityContextHolder.getContext() == null) || !(SecurityContextHolder.getContext() instanceof SecurityContext)
		        || ((SecurityContextHolder.getContext()).getAuthentication() == null)) {
		      return null;
		    }

		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth.getPrincipal() == null) {
		      return null;
		    }

		    User user = null;
		    if (auth.getPrincipal() instanceof User) {
		      user = (User) auth.getPrincipal();
		    }

		    return user;
		  }

}

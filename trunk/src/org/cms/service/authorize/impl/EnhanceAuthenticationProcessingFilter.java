/**
 * 
 */
package org.cms.service.authorize.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cms.core.utils.StringUtil;
import org.cms.dao.BaseDAO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationProcessingFilter;


/**
 * @author Administrator
 * 
 */
public class EnhanceAuthenticationProcessingFilter extends AuthenticationProcessingFilter {
	private final Integer FAIL_COUNT = 1;

	protected void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException,
			IOException {
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
			throws IOException {
		Map<String, Integer> failMap = (Map<String, Integer>) request.getSession().getAttribute("fail_count_Map");
		if (failMap != null)
			failMap.clear();
	}
	private AuthManagerImp userManager;
	
	public AuthManagerImp getUserManager() {
		return userManager;
	}

	public void setUserManager(AuthManagerImp userManager) {
		this.userManager = userManager;
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
		HttpSession session = request.getSession();
		Boolean validFlag = (Boolean) (session.getAttribute("valid_flag"));
		if (validFlag == true) {
			Map<String, Integer> failMap = (Map<String, Integer>) session.getAttribute("fail_count_Map");
			String userid = StringUtil.ConvertToStr(session.getAttribute("curr_user_id"));
//			Integer failCount = LogisticUtil.ConvertToInteger(failMap.get(userid));
//			if (failCount > FAIL_COUNT) {
//				String userID = failed.getAuthentication().getName();
//				if (!userManager.isLock(userID))
//					userManager.LockUser(userID);
//			} else {
//				failMap.put(userid, ++failCount);
//			}
		}
	}
}

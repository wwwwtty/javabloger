package org.cms.service.authorize.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.cms.core.utils.SessionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * @author Administrator
 *	用户登出系统
 */
public class AuthLogout extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {
Logger log=Logger.getLogger(AuthLogout.class);
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		SessionUtils.removeUserID(request.getSession());
		log.info("用户("+authentication.getName()+")成功登出系统!");
		 super.handle(request, response, authentication);
	}
}

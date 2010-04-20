package org.cms.service.authorize.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cms.core.exception.AuthException;
import org.cms.core.utils.SessionUtils;
import org.cms.doamin.auth.User;
import org.cms.service.authorize.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 *用户成功登陆处理;
 */
public class AuthenticationSuccessHandlerImp extends SimpleUrlAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	protected final Log logger = LogFactory.getLog(this.getClass());
	private AuthManager authManager;

    public AuthManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(AuthManager authManager) {
		this.authManager = authManager;
	}

	private RequestCache requestCache = new HttpSessionRequestCache();

     /** 成功认证*/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        User user=authManager.getUserByUsername(authentication.getName());
        if(user==null){//用户已通过权限认证,然数据库中不存在些用户;
        	throw new AuthException("系统不存在此用户!出现系统入侵!");
        }
        SessionUtils.setUserID(request.getSession(), user.getUserId());
        
        logger.info("用户("+authentication.getName()+")登陆成功!");
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        
        //跳转到默认成功页面;
        if (StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}

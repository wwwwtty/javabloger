package org.cms.service.authorize.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 *用户登陆失败处理;
 */
public class AuthenticationFailureHandlerImp extends ExceptionMappingAuthenticationFailureHandler implements
AuthenticationFailureHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	 private Map<String, String> failureUrlMap = new HashMap<String, String>();

	 @Override
	    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException exception) throws IOException, ServletException {
	        String url = failureUrlMap.get(exception.getClass().getName());
	        logger.info("用户登陆失败!");
	        if (url != null) {
	            getRedirectStrategy().sendRedirect(request, response, url);
	        } else {
	            super.onAuthenticationFailure(request, response, exception);
	        }
	    }

	    /**
	     * Sets the map of exception types (by name) to URLs.
	     *
	     * @param failureUrlMap the map keyed by the fully-qualified name of the exception class, with the corresponding
	     * failure URL as the value.
	     *
	     * @throws IllegalArgumentException if the entries are not Strings or the URL is not valid.
	     */
	    public void setExceptionMappings(Map<?,?> failureUrlMap) {
	        this.failureUrlMap.clear();
	        for (Map.Entry<?,?> entry : failureUrlMap.entrySet()) {
	            Object exception = entry.getKey();
	            Object url = entry.getValue();
	            Assert.isInstanceOf(String.class, exception, "Exception key must be a String (the exception classname).");
	            Assert.isInstanceOf(String.class, url, "URL must be a String");
	            Assert.isTrue(UrlUtils.isValidRedirectUrl((String)url), "Not a valid redirect URL: " + url);
	            this.failureUrlMap.put((String)exception, (String)url);
	        }
	    }
}

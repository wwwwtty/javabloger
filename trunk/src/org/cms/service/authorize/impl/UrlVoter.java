/**
 * 
 */
package org.cms.service.authorize.impl;

import java.util.Collection;
import java.util.List;

import org.cms.core.utils.SessionUtils;
import org.cms.doamin.auth.Function;
import org.cms.service.authorize.AuthManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * @author Administrator
 * 
 */
public class UrlVoter implements AccessDecisionVoter {
	protected final Logger logger = LoggerFactory.getLogger(UrlVoter.class);

	private AuthManager authManager;
	private String defaultPages;

	public String getDefaultPages() {
		return defaultPages;
	}

	public void setDefaultPages(String defaultPages) {
		this.defaultPages = defaultPages;
	}

	public AuthManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(AuthManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**投票决定用户有无资源访问权限  */
	@Override
	public int vote(Authentication authentication, Object obj,
			Collection<ConfigAttribute> attributes) {
		FilterInvocation filter=(FilterInvocation) obj;
		String url = filter.getRequestUrl();
	
		logger.info("系统投票测试");
		//已登陆用户权限拦截;
		if (authentication!=null && authentication.getClass()!=AnonymousAuthenticationToken.class){
			
			if(defaultPages!=null){//默认登陆用户可访问页面;
				String dps[]=defaultPages.split(";");
				for(String path:dps){
					if(compareULR(path,url)){
						logger.info("用户:"+authentication.getName()+"; 访问资源:" + url);
						return ACCESS_GRANTED;
					}
				}
			}
			//对用户访问页面权限判断;
			String userID=SessionUtils.getUserID(filter.getHttpRequest().getSession());
			List<Function> fs=authManager.getFunctionByUser(userID);
			
			boolean passed=false;
			for(Function f:fs){
				if(compareULR(f.getUrl(),url)){
					passed=true;
					break;
				}
			}
			if(passed){
				logger.info("用户:"+authentication.getName()+"; 访问资源:" + url);
				return ACCESS_GRANTED;
			}else{
				logger.info("用户:"+authentication.getName()+"; 访问资源:" + url +";拒绝访问 :权限不足.");
				return ACCESS_DENIED;
			}
		}
		else{
			logger.info("访问资源:" + url +";拒绝访问:未登陆用户; 访问用户:"+filter.getRequest().getRemoteAddr());
			return ACCESS_DENIED;
		}
	}
	
	private boolean compareULR(String orginal,String present){
		if(orginal==null||present==null){
			return false;
		}
		int m=orginal.lastIndexOf("?")>0?orginal.lastIndexOf("?"):orginal.length();
		String o=orginal.substring(0,m);
		
		int n=present.lastIndexOf("?")>0?present.lastIndexOf("?"):present.length();
		String p=present.substring(0,n);
		return p.endsWith(o);
	}
}

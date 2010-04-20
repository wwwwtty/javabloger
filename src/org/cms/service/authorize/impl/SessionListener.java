package org.cms.service.authorize.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cms.core.CmsSynchronizedService;
import org.cms.core.utils.StringUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;


public class SessionListener implements ApplicationListener {
	private static final Log log = LogFactory.getLog(SessionListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof HttpSessionCreatedEvent) {
			CmsSynchronizedService.removeUser("userId");
		}
		if (event instanceof HttpSessionDestroyedEvent) {
			String userid = StringUtil
					.ConvertToStr(((HttpSessionDestroyedEvent) event).getSession().getAttribute("curr_user_id"));
			CmsSynchronizedService.removeUser(userid);
		//	LogisticUtil.removeUserFromCache(userid);
		}
	}
}

/**
 * 
 */
package org.cms.service.authorize.impl;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

/**
 * @author Administrator
 *
 */
public class ResourceCache implements InitializingBean{

	private static final Log logger = LogFactory
	.getLog(ResourceCache.class);
	private Ehcache cache;
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
		
	}
	public Ehcache getCache() {
		return cache;
	}

	public String getResourceFromCache(String resource) {
		Element element = null;
		try {
			element = cache.get(resource);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage());
		}
		if (logger.isDebugEnabled())
			logger.debug("Cache hit: " + (element != null) + "; resource: "
					+ resource);
		if (element == null)
			return null;
		else
			return (String) element.getValue();
	}

	public void putResourceInCache(String resource) {
		Element element = new Element(resource, resource);
		if (logger.isDebugEnabled())
			logger.debug("Cache put: " + element.getKey());
		cache.put(element);
	}


	public void removeResourceFromCache(String resource) {
		cache.remove(resource);
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}





}

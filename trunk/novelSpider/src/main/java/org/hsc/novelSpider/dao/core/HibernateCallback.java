package org.hsc.novelSpider.dao.core;

import org.hibernate.Session;

public interface HibernateCallback<T> {
	public T doInHibernate(Session session);
}

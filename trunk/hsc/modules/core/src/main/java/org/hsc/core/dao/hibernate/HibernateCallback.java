package org.hsc.core.dao.hibernate;

import org.hibernate.Session;

public interface HibernateCallback<T> {
	public T doInHibernate(Session session);
}

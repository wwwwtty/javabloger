package org.cms.core.multiThread;

import java.util.HashMap;
import java.util.Map;

/*
 *基于线程安全的用户缓存;对系统用户的集中管理;
 * 2010-1-17 下午05:45:34
 */
public class UserPool {
	private Map<String,UserPool> userMap=new HashMap<String,UserPool>();
	private static UserPool instance=new UserPool();
	private UserPool(){}
	
	public static UserPool getInstance(){
		return instance;
	}
	
	public synchronized void remove(String userID){
		userMap.remove(userID);
	}
	
}

package org.cms.core;

import java.util.HashMap;
import java.util.Map;

import org.cms.core.multiThread.UserPool;


/*
 *@auther heshencao 
 * 2010-1-17 下午05:30:38
 */
public class CmsSynchronizedService {
	private static Map<String, Object> synMap = new HashMap<String, Object>();
	private static Map<String,UserPool> userMap=new HashMap<String,UserPool>();
	
	public synchronized static void removeUser(String userId){
		userMap.remove(userId);
	}
	public synchronized static void addUser(String userId,UserPool user){
		userMap.put(userId,user);
	}
}

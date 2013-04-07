package org.hsc.core.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivilegeCache {
	/**缓存的用户列表*/
	private Map<String,PrivilegeUser> userMap=new HashMap<String,PrivilegeUser>();
	
	public final static PrivilegeCache instance=new PrivilegeCache();
	
	private PrivilegeCache(){};
	
	/**移除用户
	 * @param user
	 */
	public void removePrivelegeUser(String username){
		userMap.remove(username);
	}
	
	/**移除用户
	 * @param user
	 */
	public void removePrivelegeUser(PrivilegeUser user){
		userMap.remove(user.username);
	}
	
	/**添加用户
	 * @param user
	 */
	public void addPrivilege(PrivilegeUser user){
		userMap.put(user.getUsername(), user);
	}
	
	public PrivilegeUser getPrivilege(String username){
		return userMap.get(username);
	}
	
	
	public static class PrivilegeUser{
		private Integer ID;
		private String username;
		private String password;
		private String showName;
		private String roleCode;
		private Integer	userType;
		/**针对终端登陆用户，通过IP分辨*/
		private String IP;
		private Collection<PrivilegeFunctionGroup> funGroups;
		private Collection<PrivilegeFunction> funs;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Collection<PrivilegeFunction> getFuns() {
			return this.funs;
		}
		public void setFuns(Collection<PrivilegeFunction> funs) {
			this.funs = funs;
		}
		public String getShowName() {
			return showName;
		}
		public void setShowName(String showName) {
			this.showName = showName;
		}
		public String getRoleCode() {
			return roleCode;
		}
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
		public Collection<PrivilegeFunctionGroup> getFunGroups() {
			return funGroups;
		}
		public void setFunGroups(Collection<PrivilegeFunctionGroup> funGroups) {
			this.funGroups = funGroups;
		}
		public String getIP() {
			return IP;
		}
		public void setIP(String iP) {
			IP = iP;
		}
		public Integer getID() {
			return ID;
		}
		public void setID(Integer iD) {
			ID = iD;
		}
		public Integer getUserType() {
			return userType;
		}
		public void setUserType(Integer userType) {
			this.userType = userType;
		}
		
	}
	
	public static class PrivilegeFunctionGroup{
		private Integer ID;
		private String name;
		private List<PrivilegeFunction> funs;
		private String code;
		private String defaultUrl;
		private int index;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<PrivilegeFunction> getFuns() {
			return funs;
		}
		public void setFuns(List<PrivilegeFunction> funs) {
			this.funs = funs;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDefaultUrl() {
			return defaultUrl;
		}
		public void setDefaultUrl(String defaultUrl) {
			this.defaultUrl = defaultUrl;
		}
		public Integer getID() {
			return ID;
		}
		public void setID(Integer iD) {
			ID = iD;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
	}
	
	public static class PrivilegeFunction{
		private String url;
		private String[] paramters;
		private String name;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String[] getParamters() {
			return paramters;
		}
		public void setParamters(String[] paramters) {
			this.paramters = paramters;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}


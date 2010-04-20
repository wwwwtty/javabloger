package org.cms.service.authorize.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cms.dao.BaseDAO;
import org.cms.dao.impl.PramasMap;
import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.doamin.auth.User;
import org.cms.model.authorize.AuthUser;
import org.cms.model.authorize.Authority;
import org.cms.service.authorize.AuthManager;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class AuthManagerImp implements AuthManager {
	protected final Log logger = LogFactory.getLog(getClass());
	//private IGenericDao<AuthUser, String> userDao = null;
	
	@Autowired
	private BaseDAO baseDao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		List<User> list=(List<User>) baseDao.findByField("username", username, User.class);
		if(list!=null&& list.size()>0){
			return this.buildAuthUser(list.get(0));
		}else{
			return new AuthUser();
		}
	}
	public BaseDAO getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	/*
	 */
	public List<Role> getRoleByUser(String userID) {
		PramasMap map = new PramasMap();
		map.put("userID", userID);
		map.setEntity(Role.class);
		List<Role> rs= (List<Role>) baseDao.findByNamedQueryParas("sql[find_role_by_user]AuthRole", map);
		if(rs!=null){
			return rs;
		}else{
			return new ArrayList();
		}
	}

	public List<Function> getFunctionByUser(String userID) {
		PramasMap map = new PramasMap();
		map.put("userID", userID);
		map.setEntity(Function.class);
		
		return (List<Function>) baseDao.findByNamedQueryParas("sql[find_function_by_user]Function", map);
	}

	public List getGroupMess(String groupcode, String companycode) {
		PramasMap map = new PramasMap();
		map.put("groupcode", groupcode);
		map.put("companycode", companycode);
		return baseDao.findByNamedQueryParas("getGroupMess", map);
	}

	public List getGroupObj(String groupcode, String companycode) {
		PramasMap map = new PramasMap();
		map.put("groupcode", groupcode);
		map.put("companycode", companycode);
		
		List list = baseDao.findByNamedQueryParas("getGroupObj", map );
		return list;
	}


	public List getGroupRole(String groupcode) {
		PramasMap map = new PramasMap();
		map.put("groupcode", groupcode);
		List list = baseDao.findByNamedQueryParas("getGroupRole", map);
		return list;
	}


//	public List getGroupFuncU(String userid, String companycode) {
//		PramasMap map = new PramasMap();
//		map.put("userid", userid);
//		map.put("companycode", companycode);
//		map.setEntity(RoleGroup.class);
//		List list = baseDao.findByNamedQueryParas("getGroupFuncU", map);
//		return list;
//	}

	public List<Function> getFunctionByRole(String roleID){
		PramasMap map = new PramasMap();
		map.put("roleID", roleID);
		map.setEntity(Function.class);
		List list = baseDao.findByNamedQueryParas("getGroupFuncU", map);
		return list;
	}
	
//	public String[] getGroupsOfUser(String userID) {
//		PramasMap map = new PramasMap();
//		map.put("userID", userID);
//		map.setEntity(RoleGroup.class);
//		List list = baseDao.findByNamedQueryParas("sql[find_group_by_user]AuthGroup", map);
//		String[] groups = new String[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			RoleGroup g = (RoleGroup) list.get(i);
//			groups[i] = g.getGroupCode();
//		}
//		return groups;
//	}
////
//	public boolean ChangePass(String userID, String oldPass, String newPass) {
//		boolean result = false;
//		AuthUser user = userDao.get(userID);
//		ShaPasswordEncoder sha = (ShaPasswordEncoder) SpringContextUtil.getApplicationContext().getBean("passwordEncoder");
//		oldPass = sha.encodePassword(oldPass, null);
//		newPass = sha.encodePassword(newPass, null);
//		Map paraMap = new HashMap();
//		paraMap.put("pass", newPass);
//		paraMap.put("userID", userID);
//		if (user.getPassword().equals(oldPass)) {
//			int updateRow = userDao.excuteUpdate("updatePass", paraMap);
//			if (updateRow > 0) {
//				refreshPassword(newPass);
//				result = true;
//			}
//		}
//		return result;
//	}
//
//	public boolean ChangeTheme(String userID, String theme) {
//		boolean result = false;
//		Map paraMap = new HashMap();
//		paraMap.put("theme", theme);
//		paraMap.put("userID", userID);
//		UserCache userCache = (UserCache) SpringContextUtil.getApplicationContext().getBean("userCache");
//		int updateRow = userDao.excuteUpdate("changeTheme", paraMap);
//		if (updateRow > 0) {
//			if (userCache != null) {
//				User user = (User) userCache.getUserFromCache(userID);
//				user.setTheme(theme);
//				userCache.putUserInCache(user);
//			}
//			result = true;
//		}
//		return result;
//	}
//
//	public boolean LockUser(String userID) {
//		boolean result = false;
//		Map paraMap = new HashMap();
//		paraMap.put("userID", userID);
//		UserCache userCache = (UserCache) SpringContextUtil.getApplicationContext().getBean("userCache");
//		int updateRow = userDao.excuteUpdate("lockUser", paraMap);
//		if (updateRow > 0) {
//			if (userCache != null) {
//				User user = (User) userCache.getUserFromCache(userID);
//				if (user != null) {
//					user.setAccountLocked("Y");
//					userCache.putUserInCache(user);
//				}
//			}
//			result = true;
//		}
//		return result;
//	}
//
//	public boolean isLock(String userID) {
//		AuthUser user = (AuthUser) baseDao.findById(userID, AuthUser.class);
//		if (user != null)
//			return !user.isAccountNonLocked();
//		else
//			return true;
//	}
//
//	public void refreshPassword(String password) {
//		User user;
//		UserCache userCache = (UserCache) SpringContextUtil.getApplicationContext().getBean("userCache");
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		user = (User) authentication.getPrincipal();
//		String username = user.getUsername();
//		if (userCache != null)
//			userCache.removeUserFromCache(username);
//		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(username, password);
//		newAuth.setDetails(authentication.getDetails());
//		SecurityContextHolder.getContext().setAuthentication(newAuth);
//
//	}
//
//	public String[] getActionByRole2Func(String[] roles, String funccode) {
//		if (funccode.equals("") || roles.length == 0)
//			return new String[0];
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("roleCode", roles);
//		map.put("funcCode", funccode);
//		List list = baseDao.findByNamedQueryParas("actionByRole2func", map, AuthRole2FuncRelation.class);
//		HashSet set = new HashSet();
//		if (!list.isEmpty()) {
//			AuthRole2FuncRelation rela = null;
//			String tmp = "";
//			for (Object object : list) {
//				rela = (AuthRole2FuncRelation) object;
//				tmp = rela.getAction();
//				if (!tmp.equals("")) {
//					set.addAll(Arrays.asList(tmp));
//				}
//			}
//		}
//		return (String[]) set.toArray(new String[0]);
//
//	}
//
	/**得到一个用户的功能权限;
	 * @param userid
	 * @return
	 */
	public List<Function> getFunAuthAction(String userid) {
		PramasMap map=new PramasMap();
		map.put("userID", userid);
		map.setEntity(Role.class);
		return (List<Function>)this.baseDao.findByNamedQueryParas("sql[find_role_by_user]AuthRole",map);
	}
	
	/**得到权限对象(角色)
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getAuthority(String userId){
		
		List<Role> roles= this.getRoleByUser(userId);
		List<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
		for(Role r:roles){
			Authority auth=new Authority(r.getRoleName());
			org.springframework.beans.BeanUtils.copyProperties(r, auth);
			auths.add(auth);
		}
		return auths;
	}
	
	/**构建一个权限用户对象;
	 * @param user 持久层用户实体;
	 * @return
	 */
	private AuthUser buildAuthUser(User user){
		AuthUser authUser=new AuthUser();
		authUser.setEnabled(user.getEnabled());
		authUser.setLocked(user.isLocked());
		authUser.setPassword(user.getPassword());
		authUser.setUsername(user.getUsername());
		authUser.setUserId(user.getUserId());
		authUser.setAuthorities(this.getAuthority(user.getUserId()));
		return authUser;
	}
	
	public User getUserByUsername(String username){
		PramasMap map=new PramasMap();
		map.put("username", username);
		map.setEntity(User.class);
		DetachedCriteria criteria=DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("username", username));
		List<User> us= (List<User>)this.baseDao.findByCriteria(criteria);
		if(us!=null&&us.size()>0){
			return us.get(0);
		}
		return null;
	}
}

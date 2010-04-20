package org.cms.service.authorize;

import java.util.List;

import org.cms.doamin.auth.Function;
import org.cms.doamin.auth.Role;
import org.cms.doamin.auth.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*
 *继承UserDetailsService.为Sercurity提供验证方法;
 * 2010-1-17 下午08:56:17
 */
public interface AuthManager extends UserDetailsService{
	/**Security接口,加载用户信息
	 * @param s
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public UserDetails loadUserByUsername(String s)	throws UsernameNotFoundException;
	
	public List<Role> getRoleByUser(String userID);
	
	public List<Function> getFunctionByRole(String roleID);
	
	public List<Function> getFunctionByUser(String userID);
	
	public User getUserByUsername(String username);
}

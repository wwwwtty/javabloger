package org.cms.model.authorize;

import java.util.Collection;

import org.cms.doamin.auth.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUser extends User implements UserDetails {

	private static final long serialVersionUID = 5288063273026465389L;
	private Collection<GrantedAuthority> authorities;
	
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	/** 账号过期	系统默认没有用户过期的限制; */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	/** 账号锁定	 */
	@Override
	public boolean isAccountNonLocked() {
		return !super.isLocked();
	}
	
	/**	用户凭证(密码)过期.默认没有过期; */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return super.getEnabled();
	}

}

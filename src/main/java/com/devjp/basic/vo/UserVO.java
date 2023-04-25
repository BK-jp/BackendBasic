package com.devjp.basic.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class UserVO implements UserDetails {
	
	private String email;
	private String password;
	private String role;
	private Collection<? extends GrantedAuthority> authorities;
	private String name;
	private boolean expired;
	private boolean enabled;
	private boolean credentials_expired;
	private boolean locked;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentials_expired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public void setCredentials_expired(boolean credentials_expired) {
		this.credentials_expired = credentials_expired;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "UserVO [email=" + email + ", password=" + password + ", role=" + role + ", authorities=" + authorities
				+ ", name=" + name + ", expired=" + expired + ", enabled=" + enabled + ", credentials_expired="
				+ credentials_expired + ", locked=" + locked + "]";
	}
}

package com.devjp.basic.vo;

public class UserRememberMeVO {
	private String remember_me_series;
	private String remember_me_token;
	private String remember_me_limit;
	private String email;
	
	public String getRemember_me_series() {
		return remember_me_series;
	}
	public void setRemember_me_series(String remember_me_series) {
		this.remember_me_series = remember_me_series;
	}
	public String getRemember_me_token() {
		return remember_me_token;
	}
	public void setRemember_me_token(String remember_me_token) {
		this.remember_me_token = remember_me_token;
	}
	public String getRemember_me_limit() {
		return remember_me_limit;
	}
	public void setRemember_me_limit(String remember_me_limit) {
		this.remember_me_limit = remember_me_limit;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "UserRememberMeVO [remember_me_series=" + remember_me_series + ", remember_me_token=" + remember_me_token
				+ ", remember_me_limit=" + remember_me_limit + ", email=" + email + "]";
	}
}

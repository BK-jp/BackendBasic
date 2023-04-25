package com.devjp.basic.vo;

public class UserAccessVO {
	private String email;
	private String user_agent;
	private String access_date;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}
	public String getAccess_date() {
		return access_date;
	}
	public void setAccess_date(String access_date) {
		this.access_date = access_date;
	}
	
	@Override
	public String toString() {
		return "UserAccessVO [email=" + email + ", user_agent=" + user_agent + ", access_date=" + access_date + "]";
	}
}

package com.devjp.basic.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.devjp.basic.vo.UserAccessVO;
import com.devjp.basic.vo.UserRememberMeVO;

public interface UserService extends UserDetailsService {
	UserRememberMeVO selectOneRememberMeByRememberMeSeries(String cookieSeries);
	void deleteRememberMe(UserRememberMeVO vo);
	void updateRememberMe(UserRememberMeVO vo);
	void insertRecentAccess(UserAccessVO access);
}

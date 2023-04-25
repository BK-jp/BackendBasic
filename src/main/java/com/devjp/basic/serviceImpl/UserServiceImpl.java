package com.devjp.basic.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devjp.basic.service.UserService;
import com.devjp.basic.vo.UserAccessVO;
import com.devjp.basic.vo.UserRememberMeVO;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public UserRememberMeVO selectOneRememberMeByRememberMeSeries(String cookieSeries) {
		return null;
	}

	@Override
	public void deleteRememberMe(UserRememberMeVO vo) {
		
	}

	@Override
	public void updateRememberMe(UserRememberMeVO vo) {
		
	}

	@Override
	public void insertRecentAccess(UserAccessVO access) {
		
	}
}

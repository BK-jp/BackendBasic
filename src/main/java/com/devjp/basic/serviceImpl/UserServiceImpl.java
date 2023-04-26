package com.devjp.basic.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devjp.basic.dao.UserDAO;
import com.devjp.basic.service.UserService;
import com.devjp.basic.vo.UserAccessVO;
import com.devjp.basic.vo.UserRememberMeVO;
import com.devjp.basic.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = userDAO.selectOneByEmail(username);
		if(user == null) throw new UsernameNotFoundException(username);
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		
		user.setAuthorities(authorities);
		
		return user;
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

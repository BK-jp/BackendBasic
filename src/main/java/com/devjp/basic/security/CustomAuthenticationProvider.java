package com.devjp.basic.security;

import static net.logstash.logback.marker.Markers.append;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.devjp.basic.service.UserService;
import com.devjp.basic.util.GetRemoteIP;
import com.devjp.basic.vo.UserVO;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private static final Logger historyLogger = (Logger) LoggerFactory.getLogger("historyLogger");
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		System.out.println(passwordEncoder.encode(password));
		
		historyLogger.info(
            append("logUser", "GUEST")
            .and(append("userAgent", request.getHeader("User-Agent")))
            .and(append("uri", request.getRequestURI()))
            .and(append("ip", GetRemoteIP.getIP(request))),
            "로그인 시도 : "+username
        );
		
		UserVO user = (UserVO) userService.loadUserByUsername(username);
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException(username);
		}
		
		if(!user.isEnabled()) {
			throw new DisabledException(username);
		}
		
		user.setPassword(null);
		
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}

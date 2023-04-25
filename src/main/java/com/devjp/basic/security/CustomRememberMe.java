package com.devjp.basic.security;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import com.devjp.basic.service.UserService;
import com.devjp.basic.vo.UserRememberMeVO;
import com.devjp.basic.vo.UserVO;

public class CustomRememberMe extends AbstractRememberMeServices {
	
	@Autowired
	private UserService userService;
	
	SecureRandom random;

	protected CustomRememberMe(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		random = new SecureRandom();
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
		UserVO user = (UserVO) successfulAuthentication.getPrincipal();
		
		String newSeriesValue = generateTokenValue();
		String newTokenValue = generateTokenValue();
		
		int amount = 60*60*24*7;
		Date rememberMeLimit = new Date(System.currentTimeMillis() + (1000 * amount));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		UserRememberMeVO rememberMe = new UserRememberMeVO();
		
		rememberMe.setEmail(user.getUsername());
		rememberMe.setRemember_me_series(newSeriesValue);
		rememberMe.setRemember_me_token(newTokenValue);
		rememberMe.setRemember_me_limit(df.format(rememberMeLimit));
		
		
		String[] rawCookieValues = new String[] {newSeriesValue, newTokenValue};
		
		super.setCookie(rawCookieValues, amount, request, response);
	}
	
	@Override
	protected void onLoginFail(HttpServletRequest requset, HttpServletResponse response) {
//		try {
//            response.sendRedirect("/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
		
		if(cookieTokens.length != 2) {
			throw new RememberMeAuthenticationException("Wrong Cookie");
		}
		
		String cookieSeries = cookieTokens[0];
		String cookieToken = cookieTokens[1];
		
		UserRememberMeVO rememberMe = userService.selectOneRememberMeByRememberMeSeries(cookieSeries);
		
		if(rememberMe == null) {
			throw new RememberMeAuthenticationException("Invalidate series");
		}
		
		if(!cookieToken.equals(rememberMe.getRemember_me_token())) {
			userService.deleteRememberMe(rememberMe);
			throw new CookieTheftException("Invalidate token");
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date limit = df.parse(rememberMe.getRemember_me_limit());
			
			if(limit.before(new Date())) {
				userService.deleteRememberMe(rememberMe);
				throw new InvalidCookieException("RememberMe is expired");
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String newToken = generateTokenValue();
		rememberMe.setRemember_me_token(newToken);
		
		int amount = 60*60*24*7;
		Date rememberMeLimit = new Date(System.currentTimeMillis() + (1000 * amount));
		
		rememberMe.setRemember_me_limit(df.format(rememberMeLimit));
		
		userService.updateRememberMe(rememberMe);
		
		String[] rawCookieValue = new String[] {cookieSeries, newToken};
		super.setCookie(rawCookieValue, amount, request, response);
		
		return super.getUserDetailsService().loadUserByUsername(rememberMe.getEmail());
	}
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String decodedCookieValue = super.extractRememberMeCookie(request);
		
		if(decodedCookieValue != null) {
			String[] cookieTokens = super.decodeCookie(decodedCookieValue);
			
			if(cookieTokens != null && cookieTokens.length == 2) {
				UserRememberMeVO rememberMe = userService.selectOneRememberMeByRememberMeSeries(cookieTokens[0]);
				userService.deleteRememberMe(rememberMe);
			}
		}
		
		super.logout(request, response, authentication);
	}
	
	private String generateTokenValue() {
		byte[] newToken = new byte[16];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}
}

package com.devjp.basic.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	private String loginID;
	private String defaultUrl;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(authentication != null && authentication.getPrincipal() != null) {
			request.getSession().invalidate();
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		if(!request.getRequestURI().equals("/auth/logout")) {
			response.sendRedirect(defaultUrl);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", 0);
		data.put("url", defaultUrl);
		
		response.setHeader("Content-Type", "application/json");
		
		String jsonString = mapper.writeValueAsString(data);
		
		OutputStream out = response.getOutputStream();
		
		out.write(jsonString.getBytes());
		out.close();
	}
	
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}

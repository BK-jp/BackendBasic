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

import com.devjp.basic.util.AjaxChecker;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(authentication != null && authentication.getPrincipal() != null) {
			request.getSession().invalidate();
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		if(!request.getRequestURI().equals("/auth/logout")) {
			response.sendRedirect("/");
		}
		
		if(AjaxChecker.check(request)) {
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("code", 0);
			data.put("url", "/");
			
			response.setHeader("Content-Type", "application/json");
			
			String jsonString = mapper.writeValueAsString(data);
			
			OutputStream out = response.getOutputStream();
			
			out.write(jsonString.getBytes());
			out.close();
		}else {
			response.sendRedirect("/");
		}
	}
}

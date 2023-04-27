package com.devjp.basic.security;

import static net.logstash.logback.marker.Markers.append;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.devjp.basic.util.AjaxChecker;
import com.devjp.basic.util.GetRemoteIP;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFailureHandler implements AuthenticationFailureHandler{
    
    private static final Logger historyLogger = (Logger) LoggerFactory.getLogger("historyLogger");
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String errormsg = null;
		
		if(exception instanceof UsernameNotFoundException) {
			errormsg = "ID/Password is Mismatch.";
		}else if(exception instanceof BadCredentialsException) {
			errormsg = "ID/Password is Mismatch.";
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errormsg = "Cannot read user authentication.";
		}else if(exception instanceof DisabledException) {
			errormsg = "Account is Disabled.";
		}else if(exception instanceof CredentialsExpiredException) {
			errormsg = "Password is Expired.";
		}else if(exception instanceof AccountExpiredException) {
			errormsg = "Account is Expired.";
		}
		
		historyLogger.info(
            append("logUser", "GUEST")
            .and(append("userAgent", request.getHeader("User-Agent")))
            .and(append("uri", request.getRequestURI()))
            .and(append("ip", GetRemoteIP.getIP(request)))
            .and(append("data", exception.getMessage())),
            "Login Failure : "+errormsg
        );
		
		String targetUrl = "";
		
		if(AjaxChecker.check(request)) {
		    response.setHeader("Content-Type", "application/json");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("code", 1000);
			data.put("message", errormsg);
			data.put("targetUrl", targetUrl);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String jsonString = mapper.writeValueAsString(data);
			
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
			out.close();
		}else {
			response.sendRedirect(targetUrl);
		}
	}
}

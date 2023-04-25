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
			errormsg = "아이디 혹은 비밀번호가 일치하지 않습니다.";
		}else if(exception instanceof BadCredentialsException) {
			errormsg = "아이디 혹은 비밀번호가 일치하지 않습니다.";
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errormsg = "권한 정보를 읽울 수 없습니다.";
		}else if(exception instanceof DisabledException) {
			errormsg = "비활성화된 계정입니다.";
		}else if(exception instanceof CredentialsExpiredException) {
			errormsg = "비밀번호가 만료되었습니다.";
		}else if(exception instanceof AccountExpiredException) {
			errormsg = "사용할 수 없는 계정입니다.";
		}
		
		historyLogger.info(
            append("logUser", "GUEST")
            .and(append("userAgent", request.getHeader("User-Agent")))
            .and(append("uri", request.getRequestURI()))
            .and(append("ip", GetRemoteIP.getIP(request))),
            "로그인 실패 : "+errormsg
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

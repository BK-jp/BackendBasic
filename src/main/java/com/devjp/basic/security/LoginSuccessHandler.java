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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.devjp.basic.service.UserService;
import com.devjp.basic.util.GetRemoteIP;
import com.devjp.basic.vo.UserAccessVO;
import com.devjp.basic.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private static final Logger historyLogger = (Logger) LoggerFactory.getLogger("historyLogger");
	
	private String loginID;
	private String defaultUrl;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest saveRequest = requestCache.getRequest(request, response);
		
		String targetUrl = request.getContextPath();
		
		if(saveRequest != null) {
			targetUrl = saveRequest.getRedirectUrl();
		}else {
			targetUrl = defaultUrl;
		}
		
		UserVO user = (UserVO) userService.loadUserByUsername(authentication.getName());
		
		UserAccessVO access = new UserAccessVO();
		access.setEmail(user.getUsername());
		access.setUser_agent(request.getHeader("User-Agent"));
		
		userService.insertRecentAccess(access);
		
		historyLogger.info(
            append("logUser", "GUEST")
            .and(append("userAgent", request.getHeader("User-Agent")))
            .and(append("uri", request.getRequestURI()))
            .and(append("ip", GetRemoteIP.getIP(request))),
            "로그인 성공"
        );
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", 0);
		data.put("targetUrl", targetUrl);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = mapper.writeValueAsString(data);
		
		OutputStream out = response.getOutputStream();
		
		response.setHeader("Content-Type", "application/json");
		
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

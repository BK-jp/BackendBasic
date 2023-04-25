package com.devjp.basic.security;

import static net.logstash.logback.marker.Markers.append;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.metadata.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.devjp.basic.util.AjaxChecker;
import com.devjp.basic.util.GetRemoteIP;
import com.devjp.basic.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
    
    private static final Logger historyLogger = (Logger) LoggerFactory.getLogger("historyLogger");

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String targetUrl = request.getContextPath();
		String requestUrl = request.getRequestURI();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		historyLogger.warn(
            append("logUser", "")
            .and(append("userAgent", request.getHeader("User-Agent")))
            .and(append("uri", request.getRequestURI()))
            .and(append("ip", GetRemoteIP.getIP(request)))
            .and(append("data", accessDeniedException.getMessage())),
            "접근 권한 없음"
        );
		
		if(authentication == null) {
			targetUrl = "/";
			response.sendRedirect(targetUrl);
		}else {
			UserVO user = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(AjaxChecker.check(request)) {
			    Map<String, Object> data = new HashMap<String, Object>();
                OutputStream out = response.getOutputStream();
                
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                
                ObjectMapper mapper = new ObjectMapper();
                data.put("code", 403);
                data.put("message", "해당 페이지에 접근할 권한이 없습니다.");
                String jsonString = mapper.writeValueAsString(data);
                
                out.write(jsonString.getBytes());
                out.flush();
			}else {
			    if(requestUrl.startsWith("/admin")) {
	                request.getRequestDispatcher("/WEB-INF/views/error/accessDenied.jsp").forward(request, response);
	            }else {
	                if(user != null) {
	                    targetUrl = "/contents";
	                }else {
	                    targetUrl = "/";
	                }
	                
	                response.sendRedirect(targetUrl);
	            }
			}
		}
	}
}

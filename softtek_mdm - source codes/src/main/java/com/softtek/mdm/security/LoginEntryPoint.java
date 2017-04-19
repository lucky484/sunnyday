package com.softtek.mdm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class LoginEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String targetUrl=null;
		String url=request.getRequestURI();
		if(url.indexOf("manager")>0){
			targetUrl="/manager";
		}else{
			targetUrl="/";
		}
		targetUrl=request.getContextPath()+targetUrl;
		response.sendRedirect(targetUrl);
	}

}

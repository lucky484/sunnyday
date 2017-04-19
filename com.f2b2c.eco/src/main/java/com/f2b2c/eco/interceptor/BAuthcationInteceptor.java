package com.f2b2c.eco.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.status.StorageStatus;

public class BAuthcationInteceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		@SuppressWarnings("unused")
		HandlerMethod handlerMethod;
		if (handler instanceof HandlerMethod) {
			handlerMethod = (HandlerMethod) handler;
		} else {
			return true;
		}
		BUserModel user = (BUserModel)request.getSession().getAttribute(StorageStatus.MARKET_USER.name());
		if(user != null){
			return true;
		}else{
			response.sendRedirect(request.getContextPath() + "/market");   //请求不存在，返回404页面
			return false;
		}
	}
}

package com.f2b2c.eco.interceptor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.FMenuService;
import com.f2b2c.eco.status.StorageStatus;

public class AuthcationInteceptor extends HandlerInterceptorAdapter {
   
	@Autowired
	private FMenuService fMenuService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		HandlerMethod handlerMethod;
//		if (handler instanceof HandlerMethod) {
//			handlerMethod = (HandlerMethod) handler;
//		} else {
//			return true;
//		}
		List<String> urlList = (List<String>) request.getSession().getAttribute("urlList");
		if(urlList != null && urlList.size() > 0){
			if(urlList.contains(request.getServletPath())){
				FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
				if (user != null) {
					List<String> authorityUrls = (List<String>) request.getSession().getAttribute("authenUrl");
					// 用户权限URL中不包含当前访问的url
					if (!authorityUrls.contains(request.getServletPath())) {
						response.sendRedirect(request.getContextPath() + "/farm/401");   //无权访问跳转到401页面
						return false;
					}
				} else {
					response.sendRedirect(request.getContextPath() + "/farm");
					return false;
				}
			}else{
				response.sendRedirect(request.getContextPath() + "/farm/404");   //请求不存在，返回404页面
				return false;
			}
		}else{
			response.sendRedirect(request.getContextPath() + "/farm"); //session失效退回登录页
			return false;
		}
		return true;
	}
}

package com.softtek.pst.intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.softtek.pst.model.UserModel;

public class LogIntercepter extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(LogIntercepter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod;
		if (handler instanceof HandlerMethod) {
			handlerMethod = (HandlerMethod) handler;
		} else {
			return true;
		}

		// HandlerMethod handlerMethod = (HandlerMethod) handler;
//		logger.info("preHandle: " + handlerMethod.getBeanType().getName() + "/" + handlerMethod.getMethod().getName() + "/" + handlerMethod.getMethodParameters());
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/usersManagement/users/login.do");
		} else {
			List<String> authorityUrls = (List<String>) request.getSession().getAttribute("authorityUrls");
			// 用户权限URL中不包含当前访问的url
			if (!authorityUrls.contains(request.getServletPath())) {
				response.sendRedirect(request.getContextPath() + "/error/401.do");
			}
//			int sign = 0;
//			String path = request.getServletPath();
//			List<RoleAuthority> roles = user.getRole().getRoleAuthorities();
//			for (RoleAuthority roleAuthority : roles) {
//				String url = roleAuthority.getAuthority().getAuthorityUrl();
//				if (path.equals(url)) {
//					sign = 1;
//					break;
//				} else {
//					sign = 0;
//				}
//			}
//			if (sign == 0) {
//				response.sendRedirect(request.getContextPath() + "/error/401.do");
//			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		logger.info("postHandle: " + handler.getClass());
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//		logger.info("afterCompletion: " + handler.getClass());
		super.afterCompletion(request, response, handler, ex);
	}

}

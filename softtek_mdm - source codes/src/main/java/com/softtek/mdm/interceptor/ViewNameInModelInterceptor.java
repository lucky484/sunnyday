package com.softtek.mdm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInModelInterceptor extends HandlerInterceptorAdapter {

	/** spring view name key in model. */
	public static final String SPRING_VIEW_NAME = "springViewName";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String viewName = null;
		if (modelAndView != null){
			viewName=modelAndView.getViewName();
		}
		if (viewName != null && !viewName.startsWith("redirect:")) {
            modelAndView.addObject(SPRING_VIEW_NAME, modelAndView.getViewName());
        }
		super.postHandle(request, response, handler, modelAndView);
	}

}

package com.softtek.mdm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DeviceInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 拦截设备访问系统的认证请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
		/*boolean flag=false;
		String uri=request.getRequestURI();
		String[] uriArr=StringUtil.split(uri, "/");
		for (String str : uriArr) {
			if(StringUtil.equals(str, "terminal")){
				flag=true;
				break;
			}
		}
		if(flag==true){
			UserModel user =(UserModel) request.getSession().getAttribute("customer");
			if(user==null){
				boolean isLogin=false;
				for (String str : uriArr) {
					if(StringUtil.equals(str, "login")){
						isLogin=true;
						break;
					}
				}
				if(StringUtil.equals(request.getMethod().toLowerCase(), "post")&&isLogin){
					return true;
				}else{
					return false;
				}
			}else{
				String token=request.getHeader("token");
				if(StringUtil.equals(user.getToken(), token)){
					return true;
				}else{
					return false;
				}
			}
		}else {
			return true;
		}*/
	}

}

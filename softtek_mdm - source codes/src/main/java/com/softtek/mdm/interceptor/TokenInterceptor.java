package com.softtek.mdm.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.softtek.mdm.annotation.Token;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;

/**
 * token防重复提交过滤器
 * @author jane.hui
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if(annotation!=null){
				boolean needSaveSession = annotation.needSaveToken();
				if(needSaveSession){
					request.getSession().setAttribute("token", CommUtil.generate32UUID());
				}
				boolean needRemoveSession = annotation.needRemoveToken();
				if(needRemoveSession){
					if(isRepeatSubmit(request)){
						return false;
					}
					request.getSession().removeAttribute("token");
				}
				return true;
			} else {
			    return super.preHandle(request, response, handler);
			}
		}
		return true;
		
	}
	
	/**
	 * 判断是否重复提交
	 * @param request
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request){
		Object objServerToken = request.getSession().getAttribute("token");
		if(objServerToken==null||Constant.EMPTY_STR.equals(objServerToken)){
			return true;
		}
		String serverToken = String.valueOf(objServerToken);
		String clientToken = request.getParameter("token");
		if(clientToken==null){
			return true;
		}
		if(!serverToken.equals(clientToken)){
			return true;
		}
		return false;
	}
}

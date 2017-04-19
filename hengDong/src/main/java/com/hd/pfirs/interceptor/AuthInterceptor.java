package com.hd.pfirs.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hd.pfirs.service.UserService;

/**
 * 登录鉴权
 * @author brave.chen
 * @since 2016-01-29
 */
public class AuthInterceptor implements HandlerInterceptor
{
	@Autowired
	private UserService userService;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 登录页面
	 */
	private String loginPage = "../login.html?isLogin=error";
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String username ="";
		String password = "";
		HttpSession session = request.getSession();
		
		Map<String, String[]> paramterMap = request.getParameterMap();
		if (MapUtils.isNotEmpty(paramterMap))
		{
			String [] usernameArr = request.getParameterMap().get("username");
			String [] passwordArr = request.getParameterMap().get("password");
			if (null != usernameArr && null != passwordArr)
			{
				username = request.getParameterMap().get("username")[0];
				password = request.getParameterMap().get("password")[0];
				session.setAttribute("username", username);
				session.setAttribute("password", password);
			}
		}
		
		else
		{
			username = (String) session.getAttribute("username");
			password = (String) session.getAttribute("password");
		}
		
		
		
		if ("admin".equals(username) && "admin".equals(password))
		{
			return true;  
		}
		else
		{
			session.removeAttribute("username");
			session.removeAttribute("password");
			response.sendRedirect(loginPage);
			return false;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		// TODO Auto-generated method stub
		
	}
	
}

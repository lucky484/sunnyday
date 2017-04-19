package com.f2b.security.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.f2b.security.domain.AdminUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: 居泽平  Date: 14/11/26, 15:13
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

	/**
	 * 验证用户的头部中关于权限相关的信息
	 * 这里其实不是验证，而是根据头部的认证信息查找对应的Customer对象，找到以后塞到请求中，以供后续请求使用。
	 * 如果认证信息不正确，则请求中对应的属性为NULL。
	 *
	 * @param request  request
	 * @param response response
	 * @param handler  handler
	 * @return 始终返回true，让业务层判断，这里只做数据处理，不做验证结果的判断
	 * @throws Exception 任何可能的异常。
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean resultFlag = false;

		logger.info("***************** Interceptors  SessionInterceptor  Start *****************");
		//登录页面放行
		String url = request.getRequestURI();
		logger.info("get request URI:[" + url + "]");

		AdminUser adminUser = (AdminUser) request.getSession().getAttribute("loginAgent");
		if (adminUser != null) {
			logger.info("[" + adminUser.getLoginName() + "]帐号已登录，放行！！");
			logger.info("***************** Interceptors  SessionInterceptor  end *****************\r\n");
			resultFlag = true;
		}

		if (!resultFlag) {
			//获取其后缀
			String suffixName = getSuffixName(url);
			if ("json".equals(suffixName)) {
				response.setStatus(1001);
				logger.info("用户未登录或session已失效，ajax请求数据，设置1001状态码！！");
			} else {
				//跳转到登录页面
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
				//response.sendRedirect(basePath);
				String redirectJS = String.format("<script type=\"text/javascript\">window.top.location=\"%s\"</script>", basePath);
				response.getWriter().write(redirectJS);
				logger.info("用户未登录或session已失效，获取jsp页面，转到登录页！！");
			}
			logger.info("***************** Interceptors  SessionInterceptor  end *****************\r\n");
		}

		return resultFlag;
	}

	public String getSuffixName(String str) {
		String[] fileNames = str.split("\\.");
		if (fileNames.length > 1) {
			return fileNames[fileNames.length - 1];
		}
		return "htm";
	}
}

package com.f2b2c.eco.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jacob.shen
 */
public class UrlUtil {

	/**
	 * 获取Url基础路径，带端口号
	 *
	 * @param request
	 *            HttpServletRequest。
	 * @return Url路径
	 */
	public static String getUrlBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();// request.getRequestURI();
	}

	/**
	 * 获取Url基础路径，不带端口号
	 *
	 * @param request
	 *            HttpServletRequest。
	 * @return Url路径
	 */
	public static String getUrlBasePathNoPort(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName();
	}

	/**
	 * @param request
	 *            request
	 * @return getUrlPath
	 */
	public static String getUrlPathWithContext(HttpServletRequest request) {
		return getUrlBasePath(request) + request.getContextPath();
	}

	public static String getUrlPathWithContextNoPort(HttpServletRequest request) {
		return getUrlBasePathNoPort(request) + request.getContextPath();
	}

	public static String getFullRequestURL(HttpServletRequest request) {

		return request.getRequestURL() + "?" + request.getQueryString();
	}
}

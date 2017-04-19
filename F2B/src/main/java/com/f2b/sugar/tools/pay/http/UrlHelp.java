package com.f2b.sugar.tools.pay.http;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: 居泽平 Date: 13-7-2, 下午4:33
 */
public class UrlHelp {

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

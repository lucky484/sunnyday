package com.f2b.sugar.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: 居泽平  Date: 13-7-2, 下午4:33
 */
@SuppressWarnings("unused")
public class UrlHelp {

	/**
	 * 获取Url基础路径，带端口号
	 *
	 * @param request HttpServletRequest。
	 * @return Url路径
	 */
	public static String getUrlBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();//request.getRequestURI();
	}

	/**
	 * @param request request
	 * @return getUrlPath
	 */
	public static String getUrlPath(HttpServletRequest request) {
		return getUrlBasePath(request) + request.getContextPath();
	}

	/**
	 * 获取当前请求的完整URL
	 *
	 * @param request HttpServletRequest Object.
	 * @return String.
	 */
	public static String getFullRequestURL(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(request.getRequestURL().toString());
		if (request.getQueryString() != null) {
			stringBuilder.append("?");
			stringBuilder.append(request.getQueryString());
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取当前请求的URL
	 *
	 * @param request HttpServletRequest Object.
	 * @return String.
	 */
	public static String getRequestURL(HttpServletRequest request) {
		return request.getRequestURL().toString();// + "?" + request.getQueryString();
	}
}

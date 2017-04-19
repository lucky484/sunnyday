package com.f2b.sugar.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Liping Ye  Date: 13-6-22, 下午5:23
 */
public class WebLayerUtil {

	private final static Logger logger = LoggerFactory.getLogger(WebLayerUtil.class);

	public static void setRequestLog(HttpServletRequest request) {
		logger.info("---------------------------------------------- HttpServletRequest Start ----------------------------------------------");
		logger.info("Request Url : " + UrlHelp.getRequestURL(request));
	}

	public static void setActionStartLog() {
		logger.info("---------------------------------------------------- Action Start ----------------------------------------------------");
	}

	public static int getDefaultByNullInteger(Integer testInt, Integer defaultInt) {
		if (testInt == null) {
			return defaultInt;
		} else {
			return testInt;
		}
	}

	public static Double getDefaultByNullDouble(Double testDouble, Double defaultDouble) {
		if (testDouble == null) {
			return defaultDouble;
		} else {
			return testDouble;
		}
	}

	public static String getDefaultByNullString(String testString, String defaultString) {
		if (testString == null) {
			return defaultString;
		} else {
			return testString;
		}
	}

	public static Long getDefaultByNullLong(Long testLong, Long defaultLong) {
		if (testLong == null) {
			return defaultLong;
		} else {
			return testLong;
		}
	}
}

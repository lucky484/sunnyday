package com.f2b.sugar.tools;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: 居泽平  Date: 13-7-6, 上午9:33
 */
@SuppressWarnings("unused")
public class StringConverters {

	private final static Logger logger = LoggerFactory.getLogger(StringConverters.class);

	public final static String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

	/**
	 * 将传入的参数转换为Date类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @return Converted Date Object.
	 */
	public static Date ToDate(String convertString) {
		return ToDate(convertString, DATE_FORMAT_YEAR_MONTH_DAY);
	}

	/**
	 * 将传入的参数转换为Date类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @param pattern       日期转换格式匹配
	 * @return Converted Date Object.
	 */
	public static Date ToDate(String convertString, String pattern) {
		return ToDate(convertString, pattern, "");
	}

	/**
	 * 将传入的参数转换为Date类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @param pattern       日期转换格式匹配
	 * @param paramDesc     需要转换的参数说明(作为日志补充)
	 * @return Converted Date Object.
	 */
	public static Date ToDate(String convertString, String pattern, String paramDesc) {

		Date result = null;
		if (!StringUtils.isEmpty(convertString)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				sdf.setLenient(false);
				result = sdf.parse(convertString);
				logger.debug("转换成功[String-Date]，输入参数[{}]为[{}]", paramDesc, convertString);
			} catch (Exception ex) {
				logger.error("转换失败[String-Date]，输入参数[{}]为[{}]", paramDesc, convertString, ex);
			}
		} else {
			logger.warn("转换失败[String-Date]，输入参数[{}]为[{}]", paramDesc, convertString);
		}
		return result;
	}


	/**
	 * 将传入的参数转换为Integer类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @return Converted Integer Object.
	 */
	public static Integer ToInteger(String convertString) {
		return ToInteger(convertString, "字符串");
	}

	/**
	 * 将传入的参数转换为Integer类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @param paramDesc     需要转换的参数说明(作为日志补充)
	 * @return Converted Integer Object.
	 */
	public static Integer ToInteger(String convertString, String paramDesc) {

		Integer result = null;
		if (!StringUtils.isEmpty(convertString)) {
			try {
				result = Integer.parseInt(convertString);
				logger.debug("转换成功[String-Integer]，输入参数[{}]为[{}]", paramDesc, convertString);
			} catch (Exception ex) {
				logger.error("转换失败[String-Integer]，输入参数[{}]为[{}]", paramDesc, convertString, ex);
			}
		} else {
			logger.warn("转换失败[String-Integer]，输入参数[{}]为[{}]", paramDesc, convertString);
		}
		return result;
	}


	/**
	 * 将传入的参数转换为Long类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @return Converted Long Object.
	 */
	public static Long ToLong(String convertString) {
		return ToLong(convertString, "字符串");
	}

	/**
	 * 将传入的参数转换为Long类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @param paramDesc     需要转换的参数说明(作为日志补充)
	 * @return Converted Long Object.
	 */
	public static Long ToLong(String convertString, String paramDesc) {

		Long result = null;
		if (!StringUtils.isEmpty(convertString)) {
			try {
				result = Long.parseLong(convertString);
				logger.debug("转换成功[String-Long]，输入参数[{}]为[{}]", paramDesc, convertString);
			} catch (Exception ex) {
				logger.error("转换失败[String-Long]，输入参数[{}]为[{}]", paramDesc, convertString, ex);
			}
		} else {
			logger.warn("转换失败[String-Long]，输入参数[{}]为[{}]", paramDesc, convertString);
		}

		return result;
	}

	/**
	 * 将传入的参数转换为Double类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @return Converted Double Object.
	 */
	public static Double ToDouble(String convertString) {
		return ToDouble(convertString, "字符串");
	}

	/**
	 * 将传入的参数转换为Double类型并返回
	 *
	 * @param convertString 需要转换的参数
	 * @param paramDesc     需要转换的参数说明(作为日志补充)
	 * @return Converted Double Object.
	 */
	public static Double ToDouble(String convertString, String paramDesc) {

		Double result = null;
		if (!StringUtils.isEmpty(convertString)) {
			try {
				result = Double.parseDouble(convertString);
				logger.debug("转换成功[String-Double]，输入参数[{}]为[{}]", paramDesc, convertString);
			} catch (Exception ex) {
				logger.error("转换失败[String-Double]，输入参数[{}]为[{}]", paramDesc, convertString, ex);
			}
		} else {
			logger.warn("转换失败[String-Double]，输入参数[{}]为[{}]", paramDesc, convertString);
		}

		return result;
	}
}

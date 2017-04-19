package com.f2b2c.eco.util;

import java.util.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 共通工具类
 * @author jane.hui
 *
 */
public class CommonUtil {

	/**
	 * 生成一个6位随机数
	 * @return
	 */
	public static int nextInt() {
		int n = 0;
		while (n < 100000) {
			n = (int) (Math.random() * 1000000);
		}
		return n;
	}

	/**
	 * 生成uuid
	 * @return 返回32位的随机数
	 */
	public static String generate32UUID()
	{
	    String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;
	}
	
	/**
	 * 转换时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static DateTime generateTime(String date, String format)
	{
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime dTime = DateTime.parse(date, formatter);
		return dTime;
	}
}
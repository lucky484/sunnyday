package com.softtek.mdm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author jane.hui
 *
 */
public class DateUtil {

	/**
	 * 将Date类型时间转换成String类型:格式(yyyyMMddHHmmss)
	 * @param date:时间
	 * @return 返回字符串类型时间
	 */
	public static String getyyyyMMddHHmmss(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	return sdf.format(date);
	}
}
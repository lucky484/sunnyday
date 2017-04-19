package com.f2b2c.eco.util;

/**
 * 计算分页工具类
 * @author jane.hui
 *
 */
public class PageUtil {

	/**
	 * 计算起始页
	 * @param start 当前页
	 * @param length 分页大小
	 * @return 返回起始页
	 */
	public static int getStart(Integer start,Integer length){
    	if(start==1){
    	   return start = 0;
    	} else {
    	   return start = (start - 1) * length;
    	}
	}
}

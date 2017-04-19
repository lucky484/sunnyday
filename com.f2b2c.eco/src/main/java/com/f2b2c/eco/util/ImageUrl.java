package com.f2b2c.eco.util;

import jodd.util.StringUtil;

/**
 * 图片拆分工具类
 * @author jane.hui
 *
 */
public class ImageUrl {
	
	/**
	 * 拆分商品图片
	 * @param logoUrl：商品图片
	 * @return 获取封面图
	 */
	public static String getCoverImage(String logoUrl){
		// 商品图片拆分成数组
		if(StringUtil.isNotEmpty(logoUrl)){
			String[] urlArray = null;
			urlArray = logoUrl.split("\\|");
			if(urlArray.length>0){
				return urlArray[0];
			} else {
				return "";
			}
		} 
		return "";
	}
}

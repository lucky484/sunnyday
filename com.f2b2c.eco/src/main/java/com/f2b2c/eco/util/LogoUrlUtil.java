package com.f2b2c.eco.util;

import org.apache.commons.lang3.StringUtils;

public class LogoUrlUtil {

    public static String splitImgUrl(String url){
        String returnUrl = null;
        StringBuffer buffer = new StringBuffer();
        String path = PropertiesUtil.getValue("path");
        if(StringUtils.isNotBlank(url)){
            String[] arrs = StringUtils.split(url, "|");
            for(String arr:arrs){
                arr = path + arr;
                buffer.append(arr).append("|");
            }
        }
        returnUrl = buffer.toString().substring(0, buffer.length()-1);
        return returnUrl;
    }
    
    /**
     * 获取一张图片
     * @param url:商品图片
     * @return
     */
    public static String splitOneImgUrl(String url){
        String returnUrl = null;
        String path = PropertiesUtil.getValue("path");
        if(StringUtils.isNotBlank(url)){
            String[] arrs = StringUtils.split(url, "|");
            for(String arr:arrs){
                returnUrl = path + arr;
                return returnUrl; 
            }
        }
        return returnUrl;
    }
}

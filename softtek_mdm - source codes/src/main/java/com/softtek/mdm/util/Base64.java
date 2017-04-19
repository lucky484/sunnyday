package com.softtek.mdm.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加密工具类
 * @author jane.hui
 *
 */
@SuppressWarnings("restriction")
public class Base64 {

	/**
	 * 日志记录器
	 */
	private static Logger logger = Logger.getLogger(Base64.class);
	
    // 加密  
	public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
        	logger.error("base64 encode failed,exception message is:"+e.getMessage());;  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s; 
     }
	
    // 解密
	public static String getFromBase64(String s){
    	byte[] b = null;
    	String result = null;
    	if(s != null){
    		BASE64Decoder decoder = new BASE64Decoder();
    		try {
    			b = decoder.decodeBuffer(s);
    			result = new String(b, "utf-8");
    		} catch(Exception e){
    			logger.error("base64 decode failed,exception message is:"+e.getMessage());
    		}
    	}
    	return result;
    }
}

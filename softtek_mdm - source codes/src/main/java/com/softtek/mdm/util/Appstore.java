package com.softtek.mdm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

import jodd.servlet.UrlEncoder;

/**
 * APPStore类
 * @author jane.hui
 *
 */
public class Appstore {
    
	private static Logger logger = Logger.getLogger(Appstore.class);
	
	/**
	 * 根据关键字获取相关联的应用列表
	 * @param keywords:关键字
	 * @return 返回应用列表
	 */
	public static String get(String keywords) throws Exception {
		String sUrl = String.format("https://itunes.apple.com/search?term=%s&entity=software",UrlEncoder.encode(keywords));
		
		// 构造URL
		URL url = new URL(sUrl);
		// 打开连接
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		// 设置请求超时为10s
		con.setConnectTimeout(100000);
		// 输入流
		InputStream is = con.getInputStream();
		logger.info("open ituns url connect success,status:"+ con.getResponseCode());
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder(); 
		String line = null;  
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch(IOException e){
			logger.error("close inputstream error,error message is:"+e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e){
				logger.error("close inputstream error,error message is:"+e.getMessage());
			}
		}
		return sb.toString();
	}
}

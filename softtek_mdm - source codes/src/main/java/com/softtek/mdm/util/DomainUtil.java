package com.softtek.mdm.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * 域名工具类
 * @author jane.hui
 *
 */
public class DomainUtil {

	private static Logger logger = Logger.getLogger(DomainUtil.class);
	
	private static Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
	/**
	 * 获取一级域名
	 * @param url:网页地址
	 * @return 返回域名
	 * @throws MalformedURLException
	 */
	public static String getTopDomainWithoutSubdomain(String url) {
		String host;
		try {
			host = new URL(url).getHost().toLowerCase();
			// 此处获取值转换为小写
			Matcher matcher = pattern.matcher(host);
			while (matcher.find()) {
				return matcher.group();
			}
		} catch (MalformedURLException e) {
			logger.error("domain name resolution error,url="+url+",error message="+e.toString());
			return null;
		}
		return null;
	}
}

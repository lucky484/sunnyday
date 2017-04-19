package com.hd.pfirs.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * 获取主键
 * 
 * @author ligang.yang
 *
 */
@Component
public class CommUtil {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取主键
	 * 
	 * @return Long
	 */
	public static Long getPrimaryKey() {
		Long time = (Long) new Date().getTime();
		String time1 = String.valueOf(time);
		String time2 = time1.substring(time1.length() - 11, time1.length());
		return Long.valueOf(time2);
	}
	

	/**
	 * 时间转字符串
	 * 
	 * @param date
	 * @return
	 */
	public String changeDateToStr(Date date) {
		return "";
	}

	public String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		return sdf.format(date);
	}

	//	public static void main(String[] args) {
	//		CommUtil util = new CommUtil();
	//		System.out.println(util.getNowTime());
	//	}

	/**
	 * 字符串转时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date changeStrToDate(String str) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// //
		// idCardWebModel.setCollectTimeStr(sdf.format(idCardWebModel.getCollectTime()));
		// BASE64Encoder encoder = new BASE64Encoder();
		// String imgstr = encoder.encode(idCardWebModel.getIdCardPic());
		// idCardWebModel.setIdCardPicStr(imgstr);
		// list.add(idCardWebModel);
		return new Date();
	}

	public static String stringFormatter(List<Long> objects, String regx) {
		StringBuffer buffer = new StringBuffer();
		if (CollectionUtils.isNotEmpty(objects)) {
			for (int i = 0; i < objects.size(); i++) {
				if (i == objects.size() - 1) {
					buffer.append(objects.get(i));
				} else {
					buffer.append(objects.get(i));
					buffer.append(regx);
				}
			}
		}

		return buffer.toString();
	}

	/**
	 * 这是一个静态的缓存
	 */
	public static final Map<String, Object> SYSTEM_PARMAS = new HashMap<String, Object>();

	/**
	  * @Description: 从session中获取用户名
	  * @param      : HttpServletRequest request
	  * @return     : String
	  * @throws  
	  * @data       : 2016年2月4日 下午3:08:02   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute("username");
	}
	
	public static String getLogNumID()
	{
		Long currentNum = System.currentTimeMillis();
		String currentStr = String.valueOf(currentNum);
		Random random = new Random(currentNum);
		for (int i=0;i<19;i++){
			int subNum = random.nextInt(9);
			currentStr+=subNum;
		}
		
		return currentStr;
	}
}

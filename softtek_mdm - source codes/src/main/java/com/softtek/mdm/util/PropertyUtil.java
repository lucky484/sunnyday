package com.softtek.mdm.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取properties文件的工具类
 * @author jackson.zhu
 * @version 1.0
 */
public class PropertyUtil {

	private static PropertyUtil util = null;
	private static Map<String,Properties> props = null;
	
	private PropertyUtil(){
		
	}
	
	public static PropertyUtil getInstance(){
		if(util == null){
			props = new HashMap<String,Properties>();
			util = new PropertyUtil();
		}
		return util;
	}
	
	/**
	 * 加载配置文件
	 * @param name 文件名 不包含后缀
	 * @return
	 */
	public Properties load(String name){
		if(props.get(name) != null){
			return props.get(name);
		}else{
			Properties prop = new Properties();
			try {
				prop.load(PropertyUtil.class.getResourceAsStream("/"+name+".properties"));
				props.put(name,prop);
				return prop;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

package com.f2b2c.eco.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理json model 字符串之间的关系
 * 
 * @author color.wu
 *
 */
public class JsonUtil {

	/**
	 * 日志记录器
	 */
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 将object 转换为json对象的字符串,包含字段为null和空字符串
	 * 
	 * @param object
	 *            对象
	 * @return 字符串
	 */
	public static String parseToStr(Object obj) {
		if (null == obj) {
			logger.error("the parameter is null!");
			return null;
		}
		// 集合转换
		if (obj instanceof List) {
			logger.info("the parameter is a instanceof list!");
			JSONArray jsonArray = JSONArray.fromObject(obj);
			return jsonArray.toString();
		}
		// 非集合转换
		logger.info("the parameter is a instanceof object not a list!");
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return jsonObject.toString();
	}

	/**
	 * 将字符串转换为目标对象
	 * 
	 * @param sourceStr
	 * @param targetClass
	 * @return 返回目标对象的object格式
	 */
	@SuppressWarnings("deprecation")
	public static Object parseToObject(String sourceStr, Class<?> targetClass) {
		if (StringUtils.isEmpty(sourceStr)) {
			logger.error("the parameter sourceStr is null or empty!");
			return null;
		}
		if (sourceStr.indexOf('[') == 0) {
			JSONArray jsonArray = JSONArray.fromObject(sourceStr);
			if (null != jsonArray) {
				return JSONArray.toList(jsonArray, targetClass);
			} else {
				return null;
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(sourceStr);
		if (null != jsonObject) {
			return JSONObject.toBean(jsonObject, targetClass);
		}
		return null;
	}

	/**
	 * 将obj转换为JSONObject对象，同时去掉值为null的字段和值为空字符串的字段
	 * @param obj 需要转换的obj对象
	 * @return JSONObject对象
	 */
	public static Object parseToNoEmptyStr(Object obj) {
		if (null == obj) {
			logger.error("the parameter is null!");
			return null;
		}
		// 集合转换
		if (obj instanceof List) {
			logger.info("the parameter is a instanceof list!");
			// 获取对象的类型
			JSONArray jsonArray = new JSONArray();
			for (Object o : List.class.cast(obj)) {
				JSONObject jobj = parseObject(o);
				if (null != jobj && jobj.size() > 0) {
					jsonArray.add(jobj);
				}
			}
			return jsonArray;
		} else {
			return parseObject(obj);
		}
	}

	/**
	 * 将obj转换为JSONObject对象
	 * 
	 * @param obj
	 *            需要转换的obj对象
	 * @return JSONObject对象
	 */
	private static JSONObject parseObject(Object obj) {
		JSONObject jobj = new JSONObject();
		Field[] fields = obj.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);

		for (Field field : fields) {
			try {
				Object objField = field.get(obj);
				if (objField == null) {
					continue;
				}
				if (objField instanceof String && StringUtils.isEmpty(objField.toString())) {
					continue;
				}
				if (objField instanceof Date) {
					jobj.put(field.getName(), ((Date) objField).getTime() / 1000);
					continue;
				}
				if (objField instanceof List) {
					Object objj = parseToNoEmptyStr(objField);
					jobj.put(field.getName(), objj);
				} else {
					jobj.put(field.getName(), objField);
				}
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			}
		}
		return jobj;
	}

}

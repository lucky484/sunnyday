package com.f2b.sugar.config;

import net.sf.json.JSONObject;

/**
 * Author: 张霄鹏  Date: 14-2-10 14:55
 */
public class JsonUtil {

	/**
	 * 返回错误信息
	 *
	 * @param code 错误代码
	 * @param desc 描述
	 * @return JSONObject json对象
	 */
	public static JSONObject getSimpleReturn(String code, String desc) {

		JSONObject result = new JSONObject();

		result.put("code", code);
		result.put("desc", desc);

		return result;
	}

	/**
	 * 返回错误信息
	 *
	 * @param code 错误代码
	 * @param desc 描述
	 * @return JSONObject json对象
	 */
	public static JSONObject getSimpleReturn(int code, String desc) {
		return getSimpleReturn(code + "", desc);
	}

}

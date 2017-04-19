package com.softtek.mdm.service;

import java.util.Map;

/**
 * IOS 推送服务接口
 * 
 * @author brave.chen
 *
 */
public abstract class IosPushService
{
	/**
	 * 推送指令（包含数据处理入库和通知服务器异步线程）
	 * @param map 参数map对象
	 * @return 推送处理结果
	 */
	abstract String pushInstructions(Map<String, Object> map);
}

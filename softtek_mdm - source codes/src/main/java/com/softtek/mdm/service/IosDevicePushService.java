 package com.softtek.mdm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ios设备推送
 * @author jane.hui
 *
 */
public interface IosDevicePushService {

	/**
	 * 认证
	 * @see com.softtek.mdm.service.IosDevicePushService.checkIn(Map<String, String>)
	 * @param request
	 * @param plishMap
	 */
	@Deprecated
	public void checkIn(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 推送指令
	 * @param request
	 * @param response
	 * @see com.softtek.mdm.service.IosDevicePushService.serverUrl(Map<String, Object>)
	 * @return
	 */
	@Deprecated
	public void serverUrl(HttpServletRequest request,HttpServletResponse response);	
	
	//-----------------add by color start---------------------------
	/**
	 * iOS设备第一次安装的时候会进行调用checkIn方法
	 * @param map 需要传入的参数
	 * * map
	 * |-userId
	 * |-uuid
	 * |-iosUuid
	 * |-+ plistMap
	 * 	 |-...
	 * @return
	 */
	public String checkIn(Map<String, String> map);
	/**
	 * iOS serverUrl 进行回调处理
	 * @param map
	 * @return
	 */
	public String serverUrl(Map<String, Object> map);
	//-----------------add by color end---------------------------
}

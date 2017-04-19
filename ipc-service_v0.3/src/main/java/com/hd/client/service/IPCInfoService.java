package com.hd.client.service;

import java.util.Map;

import com.hd.client.model.IPCInfoModel;

/**
 * 收集采集端电脑的设备信息
 * 
 * @author ligang.yang
 *
 */
public interface IPCInfoService {

	/**
	 * 身份证设备是否正常
	 * 
	 * @return boolean
	 */
	boolean IdCardDevOK();

	/**
	 * 人脸采集设备是否正常
	 * 
	 * @return boolean
	 */
	boolean FaceDevOK();

	/**
	 * 保存本地采集设备信息到数据库
	 * 
	 * @param model
	 */
	void saveIPCInfo(IPCInfoModel model);

	/**
	 * 拿到需要发送中的设备信息， 并更新状态为发送中
	 * 
	 * @return
	 */
	Map<String, Object> getIPCInfo();

	void updateIPCInfo(String MonitID, String relayFlag);
}

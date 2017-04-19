package com.hd.client.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.client.model.IPCInfoModel;

/**
 * 保存采集设备设备信息
 * 
 * @author ligang.yang
 *
 */
public interface IPCInfoDao {

	/**
	 * 保存采集设备设备信息
	 * 
	 * @param modal
	 * @return
	 */
	public void saveIPCInfo(IPCInfoModel model);

	/**
	 * 将设备信息查上来并更新转发状态为发送中
	 * 
	 * @return
	 */
	Map<String, Object> getAndUpdateIPCInfo();

	void updateIPInfo(@Param(value = "monitID") String monitID, @Param(value = "relayFlag") String relayFlag);

}

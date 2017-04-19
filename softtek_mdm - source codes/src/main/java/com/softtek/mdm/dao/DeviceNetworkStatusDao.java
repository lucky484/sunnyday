package com.softtek.mdm.dao;

import com.softtek.mdm.model.DeviceNetworkStatusModel;

public interface DeviceNetworkStatusDao extends CrudMapper<DeviceNetworkStatusModel, Integer> {

	/**
	 * 根据设备id删除所属的网络信息
	 * @param did
	 * @return
	 */
	int truncateWithDeviceId(Integer did);
	
	int update(DeviceNetworkStatusModel deviceNetworkStatus);
}

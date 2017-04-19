package com.softtek.mdm.service;

import com.softtek.mdm.model.DeviceNetworkStatusModel;

public interface DeviceNetworkStatusService {

	int save(DeviceNetworkStatusModel entity);
	
	DeviceNetworkStatusModel findOne(int id);
	
	int truncateWithDeviceId(Integer did);
	
	int update(DeviceNetworkStatusModel deviceNetworkStatus);
}

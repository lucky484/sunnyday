package com.softtek.mdm.service;

import com.softtek.mdm.model.DeviceSaftyModel;

public interface DeviceSaftyService {

	int save(DeviceSaftyModel entity);
	
	int truncateWithDeviceId(Integer did);
	
	DeviceSaftyModel findOneWithDeviceId(Integer did);
	
	int update(DeviceSaftyModel entity);
	
	int updateSaftyByDeviceId(DeviceSaftyModel deviceSafty);
	
	int updateAll(DeviceSaftyModel deviceSafty);
}

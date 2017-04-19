package com.softtek.mdm.service;

import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.Page;

public interface DeviceAppInfoService {

	
	Page findByPage(Integer did,Integer start,Integer length);
	
	int save(DeviceAppInfoModel entity);
	
	int truncateWithDeviceId(Integer did);
}

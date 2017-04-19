package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceNetworkStatusDao;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.service.DeviceNetworkStatusService;
@Service
public class DeviceNetworkStatusServiceImpl implements DeviceNetworkStatusService {

	@Autowired
	private DeviceNetworkStatusDao deviceNetworkStatusDao;
	
	@Override
	public int save(DeviceNetworkStatusModel entity) {
		return deviceNetworkStatusDao.save(entity);
	}

	@Override
	public DeviceNetworkStatusModel findOne(int id) {
		return deviceNetworkStatusDao.findOne(id);
	}

	@Override
	public int truncateWithDeviceId(Integer did) {
		if(did!=null){
			return deviceNetworkStatusDao.truncateWithDeviceId(did);
		}
		return 0;
	}

	@Override
	public int update(DeviceNetworkStatusModel deviceNetworkStatus) {
		
		return deviceNetworkStatusDao.update(deviceNetworkStatus);
	}
	
	
}

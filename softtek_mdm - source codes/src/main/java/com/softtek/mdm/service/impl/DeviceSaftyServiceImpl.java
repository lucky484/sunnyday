package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceSaftyDao;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.service.DeviceSaftyService;
@Service
public class DeviceSaftyServiceImpl implements DeviceSaftyService {

	@Autowired
	private DeviceSaftyDao deviceSaftyDao;
	
	@Override
	public int save(DeviceSaftyModel entity) {
		return deviceSaftyDao.save(entity);
	}

	@Override
	public int truncateWithDeviceId(Integer did) {
		if(did!=null){
			return deviceSaftyDao.truncateWithDeviceId(did);
		}
		return 0;
	}

	@Override
	public DeviceSaftyModel findOneWithDeviceId(Integer did) {
		if(did!=null){
			return deviceSaftyDao.findOneWithDeviceId(did);
		}
		return null;
	}

	@Override
	public int update(DeviceSaftyModel entity) {
		if(entity!=null){
			return deviceSaftyDao.update(entity);
		}
		return 0;
	}

	@Override
	public int updateSaftyByDeviceId(DeviceSaftyModel deviceSafty) {
	
		return deviceSaftyDao.updateSaftyByDeviceId(deviceSafty);
	}

	@Override
	public int updateAll(DeviceSaftyModel deviceSafty) {
		
		return deviceSaftyDao.updateAll(deviceSafty);
	}

}

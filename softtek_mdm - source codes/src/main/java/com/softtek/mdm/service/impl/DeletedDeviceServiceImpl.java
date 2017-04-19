package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeletedDeviceDao;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.service.DeletedDeviceService;

@Service
public class DeletedDeviceServiceImpl implements DeletedDeviceService{
   
	
	   @Autowired
	   private DeletedDeviceDao deletedDeviceDao;

	@Override
	public int insertDevice(DeviceManagerModel deviceManager) {
		
		return deletedDeviceDao.insertDevice(deviceManager);
	}
}

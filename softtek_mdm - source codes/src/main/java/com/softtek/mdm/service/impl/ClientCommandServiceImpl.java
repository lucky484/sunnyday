package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.ClientCommandDao;
import com.softtek.mdm.model.ClientCommandModel;
import com.softtek.mdm.service.ClientCommandService;

@Service
public class ClientCommandServiceImpl implements ClientCommandService{
    
	
	@Autowired
	private ClientCommandDao clientCommandDao;
	
	@Override
	public int insertRemoveDevice(ClientCommandModel clientCommand) {
		
		return clientCommandDao.insertRemoveDevice(clientCommand);
	}

	@Override
	public int deleteDevice(String sn) {
	
		return clientCommandDao.deleteDevice(sn);
	}

	@Override
	public ClientCommandModel queryDeviceDelInfo(String sn) {
		
		return clientCommandDao.queryDeviceDelInfo(sn);
	}

	@Override
	public int queryDeviceIsExits(String sn) {
		
		return clientCommandDao.queryDeviceIsExits(sn);
	}

	@Override
	public int updateRemoveDevice(String sn) {
		
		return clientCommandDao.updateRemoveDevice(sn);
	}

}

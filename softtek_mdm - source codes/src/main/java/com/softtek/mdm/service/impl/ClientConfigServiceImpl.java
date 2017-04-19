package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.ClientConfigDao;
import com.softtek.mdm.model.ClientConfigModel;
import com.softtek.mdm.service.ClientConfigService;

@Service
public class ClientConfigServiceImpl implements ClientConfigService{
    
	
	@Autowired
	private ClientConfigDao clientConfigDao;
	
	@Override
	public int insertClientConfig(ClientConfigModel clientConfig) {
		
		return clientConfigDao.insertClientConfig(clientConfig);
	}

	@Override
	public int updateClientConfig(ClientConfigModel clientConfig) {
		
		return clientConfigDao.updateClientConfig(clientConfig);
	}

	@Override
	public ClientConfigModel queryClientConfig(Integer orgId) {
		
		return clientConfigDao.queryClientConfig(orgId);
	}

}

package com.softtek.mdm.service;

import com.softtek.mdm.model.ClientConfigModel;

public interface ClientConfigService {
   
	int insertClientConfig(ClientConfigModel clientConfig);

	int updateClientConfig(ClientConfigModel clientConfig);
	
	 ClientConfigModel queryClientConfig(Integer orgId);
}

package com.softtek.mdm.dao;

import com.softtek.mdm.model.ClientConfigModel;

public interface ClientConfigDao {
        
	
	    int insertClientConfig(ClientConfigModel clientConfig);
	    
	    int updateClientConfig(ClientConfigModel clientConfig);
	    
	    ClientConfigModel queryClientConfig(Integer orgId);
}

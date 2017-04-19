package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FAgentProfitModel;

public interface FAgentProfitDao extends CrudMapper<FAgentProfitModel, Serializable> {
    
	List<FAgentProfitModel> findWithPaginationWithMap(Map<String, Object> map);
	
	int findPaginationCount(Map<String, Object> map);

	void saveAgentProfit(FAgentProfitModel agentProfitModel);
}
package com.f2b2c.eco.service.platform;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FAgentProfitModel;

public interface FAgentProfitService {

	/**
	 * 分页获取分润的列表
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	Page<FAgentProfitModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);

	void saveAgentProfit(FAgentProfitModel agentProfitModel);

}

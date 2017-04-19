package com.f2b2c.eco.service.platform.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FAgentProfitDao;
import com.f2b2c.eco.model.platform.FAgentProfitModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FAgentProfitService;

@Service
public class FAgentProfitServiceImpl implements FAgentProfitService {

	@Autowired
	private FAgentProfitDao fAgentProfitDao;

	@Override
	public Page<FAgentProfitModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		
		int total = fAgentProfitDao.countWithMap(paramMap);
		paramMap.put("pageable", pageable);
		List<FAgentProfitModel> lists = fAgentProfitDao.findWithPagination(paramMap);
		Page<FAgentProfitModel> pages = new Pagination<>(lists,pageable,total);
		return pages;
	}

	@Override
	public void saveAgentProfit(FAgentProfitModel agentProfitModel) {
		
		fAgentProfitDao.saveAgentProfit(agentProfitModel);
		
	}
	
}

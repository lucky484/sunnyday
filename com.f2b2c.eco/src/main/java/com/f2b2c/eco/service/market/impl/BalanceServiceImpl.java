package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FAgentProfitDao;
import com.f2b2c.eco.model.platform.FAgentProfitModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.market.BalanceService;
@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	private FAgentProfitDao FAgentProfitDao;
	
	@Override
	public Page<FAgentProfitModel> findWithPagination(Map<String, Object> map) {
		Pageable pageable=(Pageable) map.get("pageable");
		List<FAgentProfitModel> content=FAgentProfitDao.findWithPaginationWithMap(map);
		int recordsTotal=FAgentProfitDao.findPaginationCount(map);
		Page<FAgentProfitModel> page=new Pagination<FAgentProfitModel>(content, pageable, recordsTotal);
		return page==null?new Pagination<FAgentProfitModel>(new ArrayList<>(),pageable,0):page;
	}

}

package com.f2b2c.eco.service.market;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.f2b2c.eco.model.platform.FAgentProfitModel;

public interface BalanceService {

	Page<FAgentProfitModel> findWithPagination(Map<String, Object> map);
}

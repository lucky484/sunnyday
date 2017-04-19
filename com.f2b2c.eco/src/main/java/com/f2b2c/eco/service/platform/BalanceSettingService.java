package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.platform.BalanceSettingModel;

public interface BalanceSettingService {

	BalanceSettingModel findFirst();
	
	int insert(BalanceSettingModel entity);
	
	int update(BalanceSettingModel entity);
}

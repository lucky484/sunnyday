package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.BalanceSettingModel;

public interface BalanceSettingDao extends CrudMapper<BalanceSettingModel, Integer> {

	BalanceSettingModel findFirst();
}

package com.f2b2c.eco.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.BalanceSettingDao;
import com.f2b2c.eco.model.platform.BalanceSettingModel;
import com.f2b2c.eco.service.platform.BalanceSettingService;

/**
 * 
 * @author color.wu
 *
 */
@Service(value="fBalanceSetting")
public class BalanceSettingServiceImpl implements BalanceSettingService {

	@Autowired
	private BalanceSettingDao  balanceSettingDao;
	@Override
	public BalanceSettingModel findFirst() {
		return balanceSettingDao.findFirst();
	}

	@Override
	public int insert(BalanceSettingModel entity) {
		if(null!=entity){
			return balanceSettingDao.insert(entity);
		}
		return 0;
	}

	@Override
	public int update(BalanceSettingModel entity) {
		if(null!=entity){
			return balanceSettingDao.update(entity);
		}
		return 0;
	}
}

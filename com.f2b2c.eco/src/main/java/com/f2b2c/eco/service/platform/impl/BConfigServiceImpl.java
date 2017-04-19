package com.f2b2c.eco.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.BConfigDao;
import com.f2b2c.eco.model.platform.BConfigModel;
import com.f2b2c.eco.service.platform.BConfigService;
import com.f2b2c.eco.status.BConfigStatusEnum;

@Service
public class BConfigServiceImpl implements BConfigService {

	@Autowired
	private BConfigDao bConfigDao;

	@Override
	public void addUserConfig(BConfigModel bConfigModel) {
		
		BConfigModel configModel = new BConfigModel();
		configModel.setUserId(bConfigModel.getUserId());
		configModel.setIsDisturbed(BConfigStatusEnum.ENABLED.toString());
		configModel.setIsEnabled(BConfigStatusEnum.ENABLED.toString());
		bConfigDao.addUserConfig(bConfigModel);
		
		
	}

	@Override
	public BConfigModel getConfigByUserId(Integer userId) {
		
		return bConfigDao.getConfigByUserId(userId);
	}

	@Override
	public void updateConfig(BConfigModel configModel) {
		
		bConfigDao.updateConfig(configModel);
		
	}
	
}

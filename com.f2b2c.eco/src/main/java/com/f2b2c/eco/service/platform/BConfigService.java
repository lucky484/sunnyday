package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.platform.BConfigModel;

public interface BConfigService {

	public void addUserConfig(BConfigModel bConfigModel);

	public BConfigModel getConfigByUserId(Integer userId);

	public void updateConfig(BConfigModel configModel);
	
	
}

package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.model.platform.BConfigModel;

public interface BConfigDao {

	void addUserConfig(BConfigModel bConfigModel);

	BConfigModel getConfigByUserId(Integer userId);

	void updateConfig(BConfigModel configModel);

}

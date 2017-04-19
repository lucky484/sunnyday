package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.platform.FUserToRoleModel;

public interface FUserToRoleService {

	void saveObject(FUserToRoleModel fUserToRoleModel);

	FUserToRoleModel getUserToRoleById(Integer userId);

	void updateObject(FUserToRoleModel fUserToRoleModel);
	
}

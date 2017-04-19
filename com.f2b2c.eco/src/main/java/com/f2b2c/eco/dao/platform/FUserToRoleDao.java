package com.f2b2c.eco.dao.platform;

import java.io.Serializable;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FUserToRoleModel;

public interface FUserToRoleDao extends CrudMapper<FUserToRoleModel, Serializable>{

	void saveObject(FUserToRoleModel fUserToRoleModel);

	FUserToRoleModel getUserToRoleById(Integer userId);

	void updateObject(FUserToRoleModel fUserToRoleModel);
   
}
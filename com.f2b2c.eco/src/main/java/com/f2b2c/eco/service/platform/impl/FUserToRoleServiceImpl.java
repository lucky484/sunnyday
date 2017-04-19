package com.f2b2c.eco.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FUserToRoleDao;
import com.f2b2c.eco.model.platform.FUserToRoleModel;
import com.f2b2c.eco.service.platform.FUserToRoleService;

@Service
public class FUserToRoleServiceImpl implements FUserToRoleService {

	@Autowired
	private FUserToRoleDao fUserToRoleDao;

	@Override
	public void saveObject(FUserToRoleModel fUserToRoleModel) {
		
		fUserToRoleDao.saveObject(fUserToRoleModel);
		
	}

	@Override
	public FUserToRoleModel getUserToRoleById(Integer userId) {
		
		return fUserToRoleDao.getUserToRoleById(userId);
	}

	@Override
	public void updateObject(FUserToRoleModel fUserToRoleModel) {
		
		fUserToRoleDao.updateObject(fUserToRoleModel);
		
	}
	
	
	
	
}

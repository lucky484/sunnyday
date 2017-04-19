package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleUserModel;

public interface DeviceRuleUserService {

	int save(DeviceRuleUserModel entity);
	
	List<DeviceRuleUserModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
}

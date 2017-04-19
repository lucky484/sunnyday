package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleDepartmentModel;

public interface DeviceRuleDepartmentService {

	int save(DeviceRuleDepartmentModel entity);
	
	List<DeviceRuleDepartmentModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
}

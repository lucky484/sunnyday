package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;

public interface DeviceRuleOperationItemRelationService {

	int save(DeviceRuleOperationItemRelationModel entity);
	
	List<DeviceRuleOperationItemRelationModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
}

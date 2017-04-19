package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleItemRelationModel;

public interface DeviceRuleItemRelationService {

	int save(DeviceRuleItemRelationModel entity);
	
	List<DeviceRuleItemRelationModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
}

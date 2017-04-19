package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleVirtualGroupModel;

public interface DeviceRuleVirtualGroupService {

	int save(DeviceRuleVirtualGroupModel entity);
	
	List<DeviceRuleVirtualGroupModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
}

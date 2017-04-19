package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleVirtualGroupModel;

public interface DeviceRuleVirtualGroupDao extends CrudMapper<DeviceRuleVirtualGroupModel, Integer> {

	List<DeviceRuleVirtualGroupModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
	/**
	 * 批量保存规则到虚拟组
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleVirtualGroupModel> list);
}

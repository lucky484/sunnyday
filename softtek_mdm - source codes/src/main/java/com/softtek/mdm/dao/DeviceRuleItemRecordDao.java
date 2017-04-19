package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleItemRecordModel;

public interface DeviceRuleItemRecordDao extends CrudMapper<DeviceRuleItemRecordModel, Integer> {

	int truncateWithIds(List<Integer> ids);
	
	/**
	 * 批量保存规则到关联表
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleItemRecordModel> list);
}

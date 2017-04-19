package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleItemRelationModel;

public interface DeviceRuleItemRelationDao extends CrudMapper<DeviceRuleItemRelationModel, Integer> {

	List<DeviceRuleItemRelationModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
	/**
	 * 批量保存规则到关联表
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleItemRelationModel> list);
}

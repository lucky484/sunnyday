package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;

public interface DeviceRuleOperationItemRelationDao extends CrudMapper<DeviceRuleOperationItemRelationModel, Integer> {

	List<DeviceRuleOperationItemRelationModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
	/**
	 * 批量保存操作到关联表
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleOperationItemRelationModel> list);
}

package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleOperationItemRecordModel;

public interface DeviceRuleOperationItemRecordDao extends CrudMapper<DeviceRuleOperationItemRecordModel, Integer>{
	
	int truncateWithIds(List<Integer> ids);
	
	/**
	 * 批量保存操作记录
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleOperationItemRecordModel> list);
}

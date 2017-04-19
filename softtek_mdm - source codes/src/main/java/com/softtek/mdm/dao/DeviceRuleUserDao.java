package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleUserModel;

public interface DeviceRuleUserDao extends CrudMapper<DeviceRuleUserModel, Integer>{

	List<DeviceRuleUserModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
	/**
	 * 批量保存规则到用户
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleUserModel> list);
}

package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.DeviceRuleDepartmentModel;

public interface DeviceRuleDepartmentDao extends CrudMapper<DeviceRuleDepartmentModel, Integer> {

	List<DeviceRuleDepartmentModel> findAllByRuleId(Integer rid);
	
	int truncateWithRuleId(Integer rid);
	
	/**
	 * 批量保存规则到部门
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceRuleDepartmentModel> list);
}

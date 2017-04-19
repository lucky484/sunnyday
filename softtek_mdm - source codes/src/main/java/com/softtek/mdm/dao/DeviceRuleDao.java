package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceRuleModel;

public interface DeviceRuleDao extends CrudMapper<DeviceRuleModel, Integer> {

	Collection<DeviceRuleModel> findByMap(Map<String, Object> map);
	
	Integer findCountByOrgId(Integer orgId);
	
	int findDeviceRoleCount(Integer orgId);
	
	int truncateWithPk(Integer id);
	
	/**
	 * 根据主键，返回当前规则下所包含的用户id集合
	 * @param id
	 * @return
	 */
	List<Integer> findUserIdsByPk(Integer id);

	/**
	 * 根据条件查询设备规则数量
	 * @author brave.chen
	 * @param maps 条件map
	 * @return 数量
	 */
	Integer findCountByParams(Map<String, Object> maps);

	/**
	 * 根据设备id查询所属设备规则
	 * @param did
	 * @return
	 */
	List<Integer> findRuleIdsByDeviceId(Integer did);
	
	List<DeviceRuleModel> findRecordsByOrgId(Integer orgId);
	
	List<DeviceRuleModel> findAllActive();
	
}

package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.Page;

public interface DeviceRuleService {

	int save(DeviceRuleModel entity);
	
	String save(Map<String, Object> map);
	
	Page findWithPagination(Map<String, Object> map);

	int findDeviceRoleCount(Integer orgid);
	
	DeviceRuleModel findOne(Integer id);
	
	int update(DeviceRuleModel entity);
	
	int truncateWithPk(Integer id);
	
	/**
	 * 根据主键，返回当前规则下所包含的用户id集合
	 * @param id
	 * @return
	 */
	List<Integer> findUserIdsByPk(Integer id);
	
	/**
	 * 根据设备id查询所属设备规则
	 * @param did
	 * @return
	 */
	List<Integer> findRuleIdsByDeviceId(Integer did);
	
	List<DeviceRuleModel> findRecordsByOrgId(Integer orgId);
	
	List<DeviceRuleModel> findAllActive();
	
	void copy(Map<String, Object> map);
	/**
	 * 推送设备规则给iOS用户
	 * @param map
	 */
	void notifyToIos(Map<String, Object> map);
	
}

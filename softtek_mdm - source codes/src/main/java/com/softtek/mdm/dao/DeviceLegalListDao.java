package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceLegalListModel;

public interface DeviceLegalListDao extends CrudMapper<DeviceLegalListModel, Integer>{
	
	List<DeviceLegalListModel> findByRuleId(Integer id);
	
	List<DeviceLegalListModel> findByRuleIdWithOutDelete(Integer id);
	
	List<DeviceLegalListModel> findByRuleIdWithRuleIdAndDeviceIds(Map<String, Object> map);
	
	List<DeviceLegalListModel> findWithMap(Map<String, Object> map);
	
	int findCountByRuleId(Integer id);
	
	int findCountByRuleIdWithOutDelete(Integer id);

	List<DeviceLegalListModel> findCompliantListWithDeviceId(Map<String, Object> map);

	int findCompliantCountByDeviceId(Integer did);
	
	/**
	 * 根据设备规则id和设备id查询是否违规合规记录
	 * @param map
	 * @return
	 */
	List<DeviceLegalListModel> findOneWithMap(Map<String, Object> map);
	
	int findOneCountWithMap(Map<String, Object> map);
	
	/**
	 * 根据设备规则id和设备id将记录删除
	 * @param rid
	 * @param did
	 * @return
	 */
	int deleteRecordsByRuleIdAndDeviceId(Integer rid,Integer did);

	DeviceLegalListModel getDeviceLegalListById(Integer id);
	
	/**
	 * 查询违规记录
	 * @param map
	 * |-lastDate 时间，可以为null
	 * |-deviceId 设备id
	 * @return
	 */
	List<DeviceLegalListModel> findList(Map<String, Object> map);
}

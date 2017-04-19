package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceLegalRecordModel;

public interface DeviceLegalRecordDao extends CrudMapper<DeviceLegalRecordModel, Integer> {

	List<DeviceLegalRecordModel> findByMap(Map<String, Object> map);
	
	List<DeviceLegalRecordModel> findRecordByOrgIdMap(Map<String, Object> map);
	
	List<DeviceLegalRecordModel> findHistoryByDeviceId(Map<String, Object> map);
	
	int findCountByRidAndDid(Integer rid,Integer did);
	
	int findCountByOrgId(Integer orgId);
	
	int findLegalRecordCountByDeviceId(Integer did);

	/**
	 * 查询违规记录
	 * @author brave.chen
	 * @param map 参数map
	 * @return 违规记录列表
	 */
	List<DeviceLegalRecordModel> queryLegalRecordByParams(Map<String, Object> map);

	/**
	 * 
	 * 违规记录数量
	 *
	 * @author brave.chen
	 * @param map 参数map
	 * @return
	 */
	int queryLegalRecCountByParams(Map<String, Object> map);
	
	/**
	 * 批量保存操作记录
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<DeviceLegalRecordModel> list);

	int deleteRecordsByOrgId(Integer id);
}

package com.softtek.mdm.service;

import java.util.Map;

import com.softtek.mdm.model.DeviceLegalRecordModel;
import com.softtek.mdm.model.Page;

public interface DeviceLegalRecordService {

	int save(DeviceLegalRecordModel entity);
	
	Page findLegalRecordByRidAndDid(Integer rid,Integer did,Integer start,Integer length);
	
	int deleteWithPk(Integer id);
	
	Page findLegalRecordByOrgId(Integer orgId,Integer start,Integer length);
	
	
	Page findLegalRecordByDeviceId(Integer did,Integer start,Integer length);

	/**
	 *  查询违规记录
	 *
	 * @author brave.chen
	 * @param map 查询条件map
	 * @return 违规记录分页对象
	 */
	Page queryLegalRecordByParams(Map<String, Object> map);

	int deleteRecordsByOrgId(Integer id);
	
}

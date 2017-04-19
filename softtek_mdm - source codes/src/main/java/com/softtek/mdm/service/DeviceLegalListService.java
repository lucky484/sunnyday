package com.softtek.mdm.service;


import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.Page;

public interface DeviceLegalListService {

	Page findWithPagation(Integer rid,Integer start,Integer length);
	
	int save(DeviceLegalListModel entity);

	Page findCompliantWithDeviceId(Integer did, Integer start, Integer length);
	
	/*
	 * 根据设备id查询设备违规列表(状态，规则名称，上次检查时间，违规处理方式)
	 */
	Page findIllegalListWithDeviceId(Integer did,Integer start,Integer length);
	
	/**
	 * 根据设备规则id和设备id将记录删除
	 * @param rid
	 * @param did
	 * @return
	 */
	int deleteRecordsByRuleIdAndDeviceId(Integer rid,Integer did);
}

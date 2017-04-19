package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SysmanageDeviceLog;

public interface SysmanageDeviceLogService {
	
	/**
	 * 获取系统设备日志表中的所有事件类型
	 * @return
	 */
	List<String> getAllDeviceLogType();

	Page queryLogsByParams(Map<String, Object> paramMap);
	
	/**
	 * 根据参数获取分页对象
	 * @author brave.chen
	 * @param map 参数map
	 * @return 分页对象
	 */
	public Page queryDeviceLog(Map<String, Object> map);
	
	int insertSelective(SysmanageDeviceLog record);
    
	public String queryLogIsExite(Integer deviceId);
	
	//清掉一个月之前的各种日志
	public void deleteLogJob();
	
	Page findRecordWithDeviceId(Integer did,Integer start,Integer length);
}

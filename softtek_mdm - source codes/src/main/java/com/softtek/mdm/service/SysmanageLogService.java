package com.softtek.mdm.service;

import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SysmanageLog;

public interface SysmanageLogService {
   
	public Page queryAllOperateLog(Map<String, Object> map);
	
	public Page queryDetailLogByUserId(Integer begin, Integer num,Integer userId);
	
	public int insertSelective(SysmanageLog record);
}

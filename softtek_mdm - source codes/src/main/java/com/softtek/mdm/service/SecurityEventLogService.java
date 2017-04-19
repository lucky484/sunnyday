package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SecurityEventLogModel;

public interface SecurityEventLogService {

	 public int insertSecurityEventLog(SecurityEventLogModel securityEventLog);
	 
	 public Page queryAllSecurityLog(Map<String,Object> map);
     
	 public List<SecurityEventLogModel> querySecurityLogExport(Map<String,Object> map);
}

package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.SecurityEventLogModel;

public interface SecurityEventLogDao {

	          public int insertSecurityEventLog(SecurityEventLogModel securityEventLog);
	          
	          public List<SecurityEventLogModel> querySecurityLog(Map<String,Object> map);
	          
	          public int querySecurityLogCount(Map<String,Object> map);
	          
	          public List<SecurityEventLogModel> querySecurityLogExport(Map<String,Object> map);
}

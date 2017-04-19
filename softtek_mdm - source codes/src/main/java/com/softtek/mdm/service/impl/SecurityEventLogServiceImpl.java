package com.softtek.mdm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.SecurityEventLogDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.service.SecurityEventLogService;

@Service
public class SecurityEventLogServiceImpl implements SecurityEventLogService{

	
	@Autowired
	private SecurityEventLogDao securityEventLogDao;
	
	@Override
	public int insertSecurityEventLog(SecurityEventLogModel securityEventLog) {
		
		return securityEventLogDao.insertSecurityEventLog(securityEventLog);
	}

	@Override
	public Page queryAllSecurityLog(Map<String, Object> map) {
		Page page = new Page();
		Integer count = securityEventLogDao.querySecurityLogCount(map);
		List<SecurityEventLogModel> list = securityEventLogDao.querySecurityLog(map);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
	}

	@Override
	public List<SecurityEventLogModel> querySecurityLogExport(Map<String, Object> map) {
		
		return securityEventLogDao.querySecurityLogExport(map);
	}
}

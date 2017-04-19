package com.softtek.mdm.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.service.SysmanageDeviceLogService;

@Service("autoBackupJob")
public class AutoBackupJob {
	
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;

	
	public void deleteLogJob(){
		sysmanageDeviceLogService.deleteLogJob();
	}
}

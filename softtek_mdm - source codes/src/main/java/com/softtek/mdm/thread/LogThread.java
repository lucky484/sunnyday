package com.softtek.mdm.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.service.SecurityEventLogService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.util.SpringContextUtils;

/**
 * 日志使用线程插入，即使出错也不影响现有业务执行
 * @author color.wu
 *
 */
public class LogThread implements Runnable {
	
	private static Logger logger=LoggerFactory.getLogger(LogThread.class);

	private SysmanageDeviceLog  deviceLog;
	
	private SecurityEventLogModel securityEventLog;
	
	private SysmanageLog log;
	
	private SysmanageDeviceLogService deviceLogService;
	
	private SecurityEventLogService securityEventLogService;
	
	private SysmanageLogService sysmanageLogService;
	
	
	public LogThread(SysmanageDeviceLog  deviceLog,SecurityEventLogModel securityEventLog,SysmanageLog log){
		this.deviceLog=deviceLog;
		this.securityEventLog=securityEventLog;
		this.log=log;
		
		if(deviceLog!=null&&this.deviceLogService==null){
			this.deviceLogService=(SysmanageDeviceLogService) SpringContextUtils.getBean("sysmanageDeviceLogServiceImpl");
		}
		if(securityEventLog!=null&&this.securityEventLogService==null){
			this.securityEventLogService=(SecurityEventLogService) SpringContextUtils.getBean("securityEventLogServiceImpl");
		}
		if(log!=null&&this.sysmanageLogService==null){
			this.sysmanageLogService=(SysmanageLogService) SpringContextUtils.getBean("sysmanageLogServiceImpl");
		}
	}
	@Override
	public void run() {
		try {
			if(this.deviceLog!=null){
				deviceLogService.insertSelective(deviceLog);
			}
			if(this.securityEventLog!=null){
				securityEventLogService.insertSecurityEventLog(securityEventLog);
			}
			if(this.log!=null){
				sysmanageLogService.insertSelective(log);
			}
		} catch (Exception e) {
			logger.error("record log error, cause:"+e.getMessage());
		}
	}

}

package com.hd.pfirs.util.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.LogRecordDao;
import com.hd.pfirs.model.InterfaceLogInfo;
import com.hd.pfirs.model.OperationLogInfo;

/**
 * 行为日志记录类
 * @author brave.chen
 * @since 2016-01-20
 */
@Service
public class BehaviorLogRecordService
{
	@Autowired
	private LogRecordDao logRecordDao;
	
	/**
	 * 接口日志记录方法
	 * @param interfaceLogInfo 接口日志信息对象
	 */
	public void recordInterfaceLog(InterfaceLogInfo interfaceLogInfo)
	{
		logRecordDao.recordInterfaceLog(interfaceLogInfo);
	}
	
	/**
	 * 操作日志记录方法
	 * @param operationLogInfo 操作日志对象
	 */
	public void recordOperateLog(OperationLogInfo operationLogInfo)
	{
		logRecordDao.recordOperateLog(operationLogInfo);
	}
}

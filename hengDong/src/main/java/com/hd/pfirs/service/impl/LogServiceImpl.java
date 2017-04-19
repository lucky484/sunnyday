package com.hd.pfirs.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.LogRecordDao;
import com.hd.pfirs.dao.UserDao;
import com.hd.pfirs.model.InterfaceLogInfo;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.User;
import com.hd.pfirs.util.CommUtil;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * @author Brave.chen
 * @date Jan 6, 2016 3:08:20 PM
 */
@Service
public class LogServiceImpl implements LogService
{
	@Autowired
	private LogRecordDao logRecordDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<InterfaceLogInfo> getInterfaceLogInfos(String startTime, String endTime, String keyWords, int page)
	{
		return logRecordDao.getInterfaceLogInfos(startTime, endTime, keyWords, page);
	}

	@Override
	public List<OperationLogInfo> getOperationLogInfos(String startTime, String endTime, String keyWords, int page)
	{
		return logRecordDao.getOperationLogInfos(startTime, endTime, keyWords, page);
	}

	@Override
	public int getInterfaceLogInfosCount(String startTime, String endTime, String keyWords)
	{
		return logRecordDao.getInterfaceLogInfosCount(startTime, endTime, keyWords);
	}

	@Override
	public int getOperationLogInfosCount(String startTime, String endTime, String keyWords)
	{
		return logRecordDao.getOperationLogInfosCount(startTime, endTime, keyWords);
	}

	@Override
	public void recordInterfaceLog(InterfaceLogInfo interfaceLogInfo)
	{
		logRecordDao.recordInterfaceLog(interfaceLogInfo);
	}

	@Override
	public void recordOperateLog(OperationLogInfo operationLogInfo)
	{
//		operationLogInfo.setNumID(CommUtil.getLogNumID());
		String userName = operationLogInfo.getUserName();
		if (StringUtils.isNotEmpty(userName))
		{
			User user = userDao.getUserByUserName(userName);
			if (null != user)
			{
				operationLogInfo.setUserID(user.getIdCardNm());
				operationLogInfo.setUserName(user.getName());
			}
		}
		
		logRecordDao.recordOperateLog(operationLogInfo);
	}

}

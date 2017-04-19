package com.f2b2c.eco.service.market.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BUserOperateLogDao;
import com.f2b2c.eco.model.common.OperateLogModel;
import com.f2b2c.eco.service.market.BUserOperateLogService;

@Service
public class BUserOperateLogServiceImpl implements BUserOperateLogService{
    
	
	@Autowired
	private BUserOperateLogDao bUserOperateLogDao;
	
	@Override
	public void insertBUserOperateLog(OperateLogModel operateLog) {
		
		bUserOperateLogDao.insertBUserOperateLog(operateLog);
	}

}

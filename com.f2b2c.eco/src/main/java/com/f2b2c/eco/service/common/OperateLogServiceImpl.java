package com.f2b2c.eco.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.common.OperateLogDao;
import com.f2b2c.eco.model.common.OperateLogModel;

@Service
public class OperateLogServiceImpl implements OperateLogService{
    
	@Autowired
	private OperateLogDao operateLogDao;
	
	@Override
	public void insertOperateLog(OperateLogModel operateLog) {
		
		operateLogDao.insertOperateLog(operateLog);
	}

}

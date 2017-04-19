package com.softtek.pst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.LoggingDao;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.util.Page;

@Service
public class LoggingServiceImpl implements LoggingService{

	@Autowired
	private LoggingDao loggingDao;
	
	@Override
	public int addLogging(LoggingModel loggingModel) {
		return loggingDao.addLogging(loggingModel);
	}

	@Override
	public int getLoggingsNum() {
		return loggingDao.getLoggingsNum();
	}

	@Override
	public Page<LoggingModel> getLoggings(Integer start, Integer length,
			String column ,String dir) {
		Page<LoggingModel> page = new Page<>();
		page.setData(loggingDao.getLoggings(start,length,column,dir));
		int total = loggingDao.getLoggingsNum();
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

}

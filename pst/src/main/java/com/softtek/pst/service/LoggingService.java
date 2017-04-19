package com.softtek.pst.service;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.util.Page;

public interface LoggingService {

	public int addLogging(LoggingModel loggingModel);

	public int getLoggingsNum();

	public Page<LoggingModel> getLoggings(
			@Param(value = "start") Integer start,
			@Param(value = "length") Integer length,
			@Param(value = "col") String column,
			@Param(value = "dir") String dir);
}

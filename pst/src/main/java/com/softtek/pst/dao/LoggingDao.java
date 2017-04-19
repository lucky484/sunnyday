package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.LoggingModel;

public interface LoggingDao {

	public int addLogging(LoggingModel loggingModel);

	public int getLoggingsNum();

	public List<LoggingModel> getLoggings(
			@Param(value = "start") Integer start,
			@Param(value = "length") Integer length,
			@Param(value = "col") String column,
			@Param(value = "dir") String dir);
}

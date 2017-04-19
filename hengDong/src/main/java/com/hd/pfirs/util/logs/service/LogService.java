package com.hd.pfirs.util.logs.service;

import java.util.List;

import com.hd.pfirs.model.InterfaceLogInfo;
import com.hd.pfirs.model.OperationLogInfo;

/**
 * 日志服务接口
 * @author brave.chen
 * @since 2016-01-20
 */
public interface LogService
{
	/**
	 * 获取接口日志列表
	 * @return 接口日志列表
	 */
	List<InterfaceLogInfo> getInterfaceLogInfos(String startTime, String endTime , String keyWords, int page);
	
	/**
	 * 获取操作日志列表
	 * @return 操作日志列表
	 */
	List<OperationLogInfo> getOperationLogInfos(String startTime, String endTime , String keyWords, int page);
	
	/**
	 * 获取满足条件的接口日志条数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param keyWords 关键字
	 * @return 记录条数
	 */
	int getInterfaceLogInfosCount(String startTime, String endTime , String keyWords);
	
	/**
	 * 获取满足条件的操作日志条数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param keyWords 关键字
	 * @return 记录条数
	 */
	int getOperationLogInfosCount(String startTime, String endTime , String keyWords);
	
	/**
	 * 接口日志记录方法
	 * @param interfaceLogInfo 接口日志信息对象
	 */
	void recordInterfaceLog(InterfaceLogInfo interfaceLogInfo);

	/**
	 * 操作日志记录方法
	 * @param operationLogInfo 操作日志对象
	 */
	void recordOperateLog(OperationLogInfo operationLogInfo);
}

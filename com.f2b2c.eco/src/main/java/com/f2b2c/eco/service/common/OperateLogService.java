package com.f2b2c.eco.service.common;

import com.f2b2c.eco.model.common.OperateLogModel;

public interface OperateLogService {
  
	/**
	 * 保存操作日志
	 * @param operateLog
	 */
	void insertOperateLog(OperateLogModel operateLog);
}

package com.f2b2c.eco.service.market;

import com.f2b2c.eco.model.common.OperateLogModel;

public interface BUserOperateLogService {
    
	/**
	 * 保存日志
	 * @param operateLog
	 */
	void insertBUserOperateLog(OperateLogModel operateLog);
}

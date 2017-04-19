package com.f2b2c.eco.service.common;

/**
 * @author josen.yang
 * create time :2016-09-29 10:13
 */
public interface TimedTaskService {

	/**
	 * 3点/5点备份订单用作分析数据
	 * 
	 * @return
	 */
	void autoBakUpOrder();
	
	void clearExpiredOrder();
	
}

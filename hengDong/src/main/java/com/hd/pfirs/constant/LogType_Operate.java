/*
 * @Title      : LogType_Operate.java
 * @Package    : com.hd.pfirs.constant
 * Company:    : Softtek
 * Copyright:  : Copyright (c) 2016 
 */
package com.hd.pfirs.constant;

/**
  * @ClassName  : LogType_Operate
  * @Description: 操作日志类型
  * @date       : 2016年2月4日 下午2:40:52
  * @author     : ligang.yang@softtek.com
  */
public interface LogType_Operate {
	/**
	 * 记录日志  登录操作类型
	 */
	int type_login = 0;
	/**
	 * 记录日志  查询操作类型
	 */
	int type_query = 1;
	/**
	 * 记录日志  新增操作类型
	 */
	int type_insert = 2;
	/**
	 * 记录日志 修改操作类型
	 */
	int type_update = 3;
	/**
	 * 记录日志  删除操作类型
	 */
	int type_delete = 4;
	
	/**
	 * 成功
	 */
	String SUCCESS_TYPE = "1";
	
	/**
	 * 失败
	 */
	String FAILED_TYPE = "0";
	
	/**
	 * 用户方面的错误
	 */
	String ERROR_CAUSE_USER = "1000";
	
	/**
	 * 系统方面的错误
	 */
	String ERROR_CAUSE_SYSTEM = "2000";
}

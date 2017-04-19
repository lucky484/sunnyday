package com.hd.pfirs.constant;

/**
 * hengdong常量类
 * @author brave.chen
 * @since 2016-01-20
 */
public interface HdConstants
{
	/**
	 * 日志类型
	 */
	interface LogType
	{
		/**
		 * 用户登录日志类型
		 */
		Integer USER_LOGIN_TYPE = 1;
		
		/**
		 * 用户操作日志类型
		 */
		Integer USER_OPERATE_TYPE = 2;
		
		/**
		 * 数据导出日志类型
		 */
		Integer DATA_EXPORT_TYPE = 3;
		
		/**
		 * 数据查询日志类型日志类型
		 */
		Integer DATA_SEARCH_TYPE = 4;
		
		/**
		 * 数据更新日志类型
		 */
		Integer DATA_UPDATE_TYPE = 5;
		
		/**
		 * 授权日志类型
		 */
		Integer AUTHORIZE_TYPE = 6;
		
		/**
		 * 服务调用日志类型
		 */
		Integer SERVICE_CALL_TYPE = 7;
	}
	
	/**
	 * 菜单级别
	 * @author brave.chen
	 *
	 */
	interface MenuLevel
	{
		/**
		 * 根菜单
		 */
		String ROOT_LEVEL = "0";
		
		/**
		 * 2级子菜单
		 */
		String ONE_LEVEL = "1";
		
		/**
		 * 底层菜单
		 */
		String TWO_LEVEL = "2";
	}
}

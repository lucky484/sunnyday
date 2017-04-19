package com.f2b2c.eco.status;
/**
 * 用于储存在Request scope和Session scope域中key的值
 * 一些基本不变多出使用的key
 * @author color.wu
 *
 */
public enum StorageStatus {
	/**
	 * 前台的提示key值
	 */
	REQ_TIP,
	/**
	 * f端用户的基本信息
	 */
	USER_INSESSION,
	/**
	 * b端用户的基本信息
	 */
	MARKET_USER,
	/**
	 * 权限对于的菜单列表
	 */
	AUTHCATION_MENU_LIST,
	AUTHCATION_ROLE;
}

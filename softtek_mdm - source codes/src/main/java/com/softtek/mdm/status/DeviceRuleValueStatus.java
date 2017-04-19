package com.softtek.mdm.status;

public enum DeviceRuleValueStatus {
	/**
	 * 黑名单
	 */
	BLACK_LIST(1),
	/**
	 * 黑名单
	 */
	WHITE_LIST(0),
	/**
	 * 已破解
	 */
	CRACKED(1),
	/**
	 * 未破解
	 */
	UNCRACKED(0),
	/**
	 * =(等于)
	 */
	EQUAL(1),
	/**
	 * (>)大于
	 */
	GREAT_THAN(2),
	/**
	 * (<)小于
	 */
	LESS_THAN(3),
	/**
	 * (>=)大于等于
	 */
	GREAT_EQUAL_THAN(4),
	/**
	 * (<=)小于等于
	 */
	LESS_EQUAL_THAN(5),
	/**
	 * 已配置
	 */
	CONFIG(1),
	/**
	 * 未配置
	 */
	UNCONFIG(0),
	/**
	 * 企业设备
	 */
	ENTERPRISE_DEVICE(1),
	/**
	 * 个人设备
	 */
	PERSONAL_DEVICE(0),
	/**
	 * 在范围内
	 */
	IN_RANGE(1),
	/**
	 * 在范围外
	 */
	OUT_RANGE(0),
	/**
	 * 一致
	 */
	IN_SAME(1),
	/**
	 * 不一致
	 */
	IN_DIFFIERENT(0),
	/**
	 * 监控中
	 */
	IN_MONITOR(1),
	/**
	 * 待监控
	 */
	WAITE_MONITOR(2),
	/**
	 * 注销中
	 */
	IN_LOGOUT(3),
	/**
	 * 托管中
	 */
	IN_PIPE(4),
	/**
	 * 未丢失
	 */
	NOT_LOST(5),
	/**
	 * 已丢失
	 */
	IS_LOST(6),
	/**
	 * 未监控
	 */
	OUT_MONITOR(7),
	/**
	 * >1(大于1)
	 */
	GREAT_THAN_ONE(1),
	/**
	 * >=1(大于等于1)
	 */
	GREAT_EQUAL_THAN_ONE(1),
	/**
	 * 管理员邮件通知
	 */
	ADMIN_EMAIL_INFORM(1),
	/**
	 * 短信通知
	 */
	SMS_INFORM(2),
	/**
	 * 推送通知
	 */
	PUSH_INFORM(3),
	/**
	 * 邮件通知
	 */
	EMAIL_INFORM(4),
	/**
	 * 企业数据擦除
	 */
	ERASE_ENTERPISE_DATA(1),
	/**
	 * 恢复出厂设置
	 */
	BACK_TO_FACTORY_DEFAULT(0),
	/**
	 * 禁止登录
	 */
	LOGIN_FORBIDDEN(1),
	/**
	 * 禁止访问应用资源
	 */
	APP_RESOURCES_FORBIDDEN(2),
	/**
	 * 禁止访问文件资源
	 */
	FILE_RESOURCES_FORBIDDEN(3),
	/**
	 * Android系统
	 */
	OS_ANDROID(1),
	/**
	 * iOS系统
	 */
	OS_iOS(0),
	/**
	 * 邮件擦除
	 */
	ERASE_EMAIL(1),
	/**
	 * 禁止访问
	 */
	ACCESS_FORBIDDEN(0),
	/**
	 * 限制使用
	 */
	USE_LIMIT(1);
	
	private int displayValue;
	private DeviceRuleValueStatus(final int value){
		this.displayValue = value;
	}
	
	public int getDisplayValue(){
		return this.displayValue;
	}
}

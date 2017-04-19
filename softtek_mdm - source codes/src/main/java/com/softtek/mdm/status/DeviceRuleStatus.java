package com.softtek.mdm.status;

public enum DeviceRuleStatus {
	/**
	 * 应用黑白名单
	 */
	APPLICATION_BLACK_AND_WHITE_LIST(1),
	/**
	 * 破解状态
	 */
	CRACK_STATE(2),
	
	/**
	 * 终端型号
	 */
	TERMINAL_VERSION(3),
	/**
	 * 操作系统版本
	 */
	OS_VERSION(4),
	/**
	 * 锁屏密码
	 */
	LOCK_PASSWORD(5),
	/**
	 * 设备归属
	 */
	DEVICE_BELONG(6),
	/**
	 * 时间范围
	 */
	TIME_RANGE(7),
	/**
	 * IMSI校验
	 */
	IMSI_VERIFY(8),
	/**
	 * 设备状态
	 */
	DEVICE_STATUS(9),
	/**
	 * SIM卡数量
	 */
	SIM_COUNT(10),
	/**
	 * SD卡数量
	 */
	SD_COUNT(11),
	/**
	 * 通知
	 */
	INFORM(1),
	/**
	 * 指令
	 */
	ORDER(2),
	/**
	 * 
	 */
	TERMINAL_DEVICE(3),
	/**
	 * 策略变更
	 */
	STRATEGY_CHANGE(4),
	/**
	 * 安全邮件
	 */
	SAFT_EMAIL(5),
	/**
	 * 应用限制
	 */
	APP_LIMIT(6);
	
	private int displayValue;
	private DeviceRuleStatus(final int value){
		this.displayValue = value;
	}
	
	public int getDisplayValue(){
		return this.displayValue;
	}
}

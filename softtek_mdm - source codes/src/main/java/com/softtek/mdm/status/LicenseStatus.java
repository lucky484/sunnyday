package com.softtek.mdm.status;

public enum LicenseStatus {
	/**
	 * 日期超过限定
	 */
	BEYOND_DATE(1),
	/**
	 * 人数超过限定
	 */
	BEYOND_COUNT(2),
	/**
	 * license未验证
	 */
	FILE_NOTEXISTS(3),
	/**
	 * 没有任何超过限定，合规
	 */
	BEYONG_NONE(0);
	
	private int displayValue;
	private LicenseStatus(final int value){
		this.displayValue = value;
	}
	
	public int getDisplayValue(){
		return this.displayValue;
	}
}

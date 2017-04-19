package com.softtek.mdm.bean;

/**
 * 应用Excel Bean
 * @author jane.hui
 *
 */
public class ExcelAppBean {
	
	/**
	 * 应用id
	 */
	private String appId;
	
	/**
	 * 应用名称
	 */
	private String appName;
	
	/**
	 * 应用类型
	 */
	private String appType;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
}

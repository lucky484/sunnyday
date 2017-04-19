package com.softtek.mdm.bean;

/**
 * ios设备策略
 * @author jane.hui
 *
 */
public class IosPolicyVersionBean {

	/**
	 * 设备token
	 */
	private String deviceToken;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 策略id
	 */
	private Integer policyId;

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
}

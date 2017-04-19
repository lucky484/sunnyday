package com.softtek.mdm.model;

public class DeviceRuleUserModel {

	private Integer id;
	
	private DeviceRuleModel deviceRule;
	
	private UserModel user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DeviceRuleModel getDeviceRule() {
		return deviceRule;
	}

	public void setDeviceRule(DeviceRuleModel deviceRule) {
		this.deviceRule = deviceRule;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
	
	
}

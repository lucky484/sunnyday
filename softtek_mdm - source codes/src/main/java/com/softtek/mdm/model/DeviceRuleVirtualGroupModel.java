package com.softtek.mdm.model;

public class DeviceRuleVirtualGroupModel {

	private Integer id;
	
	private DeviceRuleModel deviceRule;
	
	private VirtualGroupModel virtualGroup;

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

	public VirtualGroupModel getVirtualGroup() {
		return virtualGroup;
	}

	public void setVirtualGroup(VirtualGroupModel virtualGroup) {
		this.virtualGroup = virtualGroup;
	}
	
	
}

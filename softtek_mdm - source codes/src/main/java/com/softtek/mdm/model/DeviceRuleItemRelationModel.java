package com.softtek.mdm.model;

public class DeviceRuleItemRelationModel {

	private Integer id;
	
	private DeviceRuleModel deviceRule;
	
	private DeviceRuleItemRecordModel deviceRuleItemRecord;

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

	public DeviceRuleItemRecordModel getDeviceRuleItemRecord() {
		return deviceRuleItemRecord;
	}

	public void setDeviceRuleItemRecord(DeviceRuleItemRecordModel deviceRuleItemRecord) {
		this.deviceRuleItemRecord = deviceRuleItemRecord;
	}
	
	
}

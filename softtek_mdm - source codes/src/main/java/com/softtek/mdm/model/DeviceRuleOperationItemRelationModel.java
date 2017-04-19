package com.softtek.mdm.model;

public class DeviceRuleOperationItemRelationModel {

	private Integer id;
	
	private DeviceRuleModel deviceRule;
	
	private DeviceRuleOperationItemRecordModel deviceRuleOperationRecord;

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

	public DeviceRuleOperationItemRecordModel getDeviceRuleOperationRecord() {
		return deviceRuleOperationRecord;
	}

	public void setDeviceRuleOperationRecord(DeviceRuleOperationItemRecordModel deviceRuleOperationRecord) {
		this.deviceRuleOperationRecord = deviceRuleOperationRecord;
	}
	
	
}

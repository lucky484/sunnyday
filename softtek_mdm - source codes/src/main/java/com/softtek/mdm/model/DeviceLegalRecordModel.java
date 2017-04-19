package com.softtek.mdm.model;

import java.util.Date;

/**
 * 设备违规历史清单
 * @author color.wu
 *
 */
public class DeviceLegalRecordModel {

	private Integer id;
	/**
	 * 设备规则对象
	 */
	private DeviceRuleModel deviceRule;
	
	private Date violate_time;
	
	/**
	 * 规则验证时间
	 */
	private String check_time;
	
	/**
	 * 违规设备规则内容
	 */
	private DeviceRuleItemRecordModel deviceRuleItemRecord;
	
	/**
	 * 设备基本信息
	 */
	private DeviceBasicInfoModel deviceBasicInfo;
	
	private String extra;
	
	
	
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

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

	public Date getViolate_time() {
		return violate_time;
	}

	public void setViolate_time(Date violate_time) {
		this.violate_time = violate_time;
	}

	public DeviceRuleItemRecordModel getDeviceRuleItemRecord() {
		return deviceRuleItemRecord;
	}

	public void setDeviceRuleItemRecord(DeviceRuleItemRecordModel deviceRuleItemRecord) {
		this.deviceRuleItemRecord = deviceRuleItemRecord;
	}

	public DeviceBasicInfoModel getDeviceBasicInfo() {
		return deviceBasicInfo;
	}

	public void setDeviceBasicInfo(DeviceBasicInfoModel deviceBasicInfo) {
		this.deviceBasicInfo = deviceBasicInfo;
	}
	
	
}

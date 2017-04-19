package com.softtek.mdm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 设备是否违规表
 * @author color.wu
 *
 */
public class DeviceLegalListModel {

	private Integer id;
	/**
	 * 设备基本信息
	 */
	private DeviceBasicInfoModel deviceBasicInfo;
	/**
	 * 设备规则
	 */
	private DeviceRuleModel deviceRule;
	
	private Date violate_time;
	/**
	 * 1违规 0合规
	 */
	private Integer status;
	
	Date delete_time;
	
	
	
	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

	/**
	 * 额外添加一个字段用户临时存储信息
	 */
	private List<DeviceRuleOperationItemRelationModel> operation;
	
	public List<DeviceRuleOperationItemRelationModel> getOperation() {
		return operation;
	}

	public void setOperation(List<DeviceRuleOperationItemRelationModel> opeation) {
		this.operation = opeation;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public DeviceBasicInfoModel getDeviceBasicInfo() {
		return deviceBasicInfo;
	}

	public void setDeviceBasicInfo(DeviceBasicInfoModel deviceBasicInfo) {
		this.deviceBasicInfo = deviceBasicInfo;
	}

	public DeviceRuleModel getDeviceRule() {
		return deviceRule;
	}

	public void setDeviceRule(DeviceRuleModel deviceRule) {
		this.deviceRule = deviceRule;
	}

	public String getViolate_time() {
		if(violate_time != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(violate_time);
		}
		return "";
	}
	

	public void setViolate_time(Date violate_time) {
		this.violate_time = violate_time;
	}

	public Integer getStatus() {
		return status==null?0:status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}

package com.softtek.mdm.model;

import java.util.Date;

/**
 * 设备规则
 * @author color.wu
 *
 */
public class DeviceRuleModel extends BaseEntity{

	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 是否激活
	 */
	private Integer isactive;
	/**
	 * 规则描述
	 */
	private String describe;
	/**
	 * 适用平台
	 */
	private Integer platform;
	/**
	 * 下次检测时间
	 */
	private Date next_check_time;
	/**
	 * 不同类别需全部匹配 ，相同规则类别1：全部匹配 2，任意匹配 
	 */
	private DeviceRuleMatchModel deviceRuleMatch;
	
	private OrganizationModel organization;
	
	
	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public DeviceRuleMatchModel getDeviceRuleMatch() {
		return deviceRuleMatch;
	}

	public void setDeviceRuleMatch(DeviceRuleMatchModel deviceRuleMatch) {
		this.deviceRuleMatch = deviceRuleMatch;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsactive() {
		return isactive;
	}

	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Date getNext_check_time() {
		return next_check_time;
	}

	public void setNext_check_time(Date next_check_time) {
		this.next_check_time = next_check_time;
	}
	
	
	
}

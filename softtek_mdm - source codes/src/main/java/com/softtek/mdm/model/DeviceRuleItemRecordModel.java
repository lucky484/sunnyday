package com.softtek.mdm.model;

public class DeviceRuleItemRecordModel {

	private Integer id;
	/**
	 * 规则类别
	 */
	private Integer type;
	/**
	 * 规则条件
	 */
	private Integer condition;
	/**
	 * 规则值
	 * 如果是黑白名单，则是json化字符串
	 */
	private String value;
	
	//*********************
	private boolean isMatch;
	
	public boolean isMatch() {
		return isMatch;
	}

	public void setIsMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}
	//***********************

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}

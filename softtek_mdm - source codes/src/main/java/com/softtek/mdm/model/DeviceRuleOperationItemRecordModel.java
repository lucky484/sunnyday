package com.softtek.mdm.model;

public class DeviceRuleOperationItemRecordModel {

	private Integer id;
	/**
	 * 操作类别
	 */
	private Integer type;
	/**
	 * 操作条件
	 */
	private Integer condition;
	/**
	 * 操作值
	 */
	private String value;

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

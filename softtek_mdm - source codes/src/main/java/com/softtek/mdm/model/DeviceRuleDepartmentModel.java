package com.softtek.mdm.model;


public class DeviceRuleDepartmentModel {

	private Integer id;
	
	private DeviceRuleModel deviceRule;
	
	private StructureModel structure;

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

	public StructureModel getStructure() {
		return structure;
	}

	public void setStructure(StructureModel structure) {
		this.structure = structure;
	}
	
	
}

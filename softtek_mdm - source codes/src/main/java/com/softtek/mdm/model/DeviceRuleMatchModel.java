package com.softtek.mdm.model;

public class DeviceRuleMatchModel {

	/**
	 * 1：全部匹配 2，任意匹配 
	 */
	private Integer id;
	
	private String neqrule;
	
	private String eqrule;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNeqrule() {
		return neqrule;
	}

	public void setNeqrule(String neqrule) {
		this.neqrule = neqrule;
	}

	public String getEqrule() {
		return eqrule;
	}

	public void setEqrule(String eqrule) {
		this.eqrule = eqrule;
	}
	
	
}

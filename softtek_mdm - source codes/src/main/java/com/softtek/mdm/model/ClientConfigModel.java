package com.softtek.mdm.model;

public class ClientConfigModel extends BaseEntity {
     
	   private Integer id;
	   
	   private Integer orgId;
	   
	   private Integer terminalUnbundEnable;   //启用终端解绑
	   
	   private Integer serviceSettingHide;     //隐藏服务器设置
	     
	   private Integer deviceInfoHide;         //隐藏设备信息

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTerminalUnbundEnable() {
		return terminalUnbundEnable;
	}

	public void setTerminalUnbundEnable(Integer terminalUnbundEnable) {
		this.terminalUnbundEnable = terminalUnbundEnable;
	}

	public Integer getServiceSettingHide() {
		return serviceSettingHide;
	}

	public void setServiceSettingHide(Integer serviceSettingHide) {
		this.serviceSettingHide = serviceSettingHide;
	}

	public Integer getDeviceInfoHide() {
		return deviceInfoHide;
	}

	public void setDeviceInfoHide(Integer deviceInfoHide) {
		this.deviceInfoHide = deviceInfoHide;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	   
}

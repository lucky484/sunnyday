package com.softtek.mdm.model;

public class DeviceNetworkStatusModel {

	private Integer id;
	
	private Integer device_id;
	
	private String vendor;
	
	private String phone;
	
	private String net_type_id;
	
	private String sim_number;
	
	private String wifi_mac;
	
	private String blue_tooth_mac;
	
	private String hot_point;
	
	private String voice_roam;
	
	private String data_roam;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDevice_id() {
		return device_id;
	}

	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNet_type_id() {
		return net_type_id;
	}

	public void setNet_type_id(String net_type_id) {
		this.net_type_id = net_type_id;
	}

	public String getSim_number() {
		return sim_number;
	}

	public void setSim_number(String sim_number) {
		this.sim_number = sim_number;
	}

	public String getWifi_mac() {
		return wifi_mac;
	}

	public void setWifi_mac(String wifi_mac) {
		this.wifi_mac = wifi_mac;
	}

	public String getBlue_tooth_mac() {
		return blue_tooth_mac;
	}

	public void setBlue_tooth_mac(String blue_tooth_mac) {
		this.blue_tooth_mac = blue_tooth_mac;
	}

	public String getHot_point() {
		return hot_point;
	}

	public void setHot_point(String hot_point) {
		this.hot_point = hot_point;
	}

	public String getVoice_roam() {
		return voice_roam;
	}

	public void setVoice_roam(String voice_roam) {
		this.voice_roam = voice_roam;
	}

	public String getData_roam() {
		return data_roam;
	}

	public void setData_roam(String data_roam) {
		this.data_roam = data_roam;
	}
	
	
	
}

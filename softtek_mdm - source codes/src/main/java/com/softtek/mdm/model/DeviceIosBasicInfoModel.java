package com.softtek.mdm.model;

import java.util.Date;

public class DeviceIosBasicInfoModel extends BaseEntity {

	public String devicePolicyName;   //策略名称
	
	public Integer deviceStatus;      //管理状态
	
	public Date collectTime;          //采集时间
	
	public String deviceName;         //设备名称
	
	public String osVersion;          //操作系统
	
	public String belong;             //设备归属
	
	public String sn;                 //序列号
	
	public String udid;               //udid
	
	public String esnorimei;          //IMEI号
	
	public String capacity;           //存储容量(剩余多少，共多少)
	
	public String vendor;             //运营商
	
	public String dataRoam;           //数据漫游
	
	public String voiceRoam;          //语音漫游
	
	public String wifiMac;            //wifi地址
	
	public String blueToothMac;       //蓝牙地址

	public String getDevicePolicyName() {
		return devicePolicyName;
	} 

	public void setDevicePolicyName(String devicePolicyName) {
		this.devicePolicyName = devicePolicyName;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getEsnorimei() {
		return esnorimei;
	}

	public void setEsnorimei(String esnorimei) {
		this.esnorimei = esnorimei;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDataRoam() {
		return dataRoam;
	}

	public void setDataRoam(String dataRoam) {
		this.dataRoam = dataRoam;
	}

	public String getVoiceRoam() {
		return voiceRoam;
	}

	public void setVoiceRoam(String voiceRoam) {
		this.voiceRoam = voiceRoam;
	}

	public String getWifiMac() {
		return wifiMac;
	}

	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}

	public String getBlueToothMac() {
		return blueToothMac;
	}

	public void setBlueToothMac(String blueToothMac) {
		this.blueToothMac = blueToothMac;
	}
	
	
}

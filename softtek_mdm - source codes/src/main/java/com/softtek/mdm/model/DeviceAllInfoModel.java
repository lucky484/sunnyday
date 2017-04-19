package com.softtek.mdm.model;

import java.util.List;
import java.util.Map;

public class DeviceAllInfoModel {

	/**
	 * 设备基本信息
	 */
	private DeviceBasicInfoModel deviceBasicInfo;
	/**
	 * 设备网络状态
	 */
	private DeviceNetworkStatusModel deviceNetworkStatus;
	/**
	 * app应用
	 */
	private List<DeviceAppInfoModel> deviceAppInfoList;
	/**
	 * 设备安全
	 */
	private DeviceSaftyModel deviceSafty;
	
	private Map<String, Object> devicePolicy;
	
	private PolicyModel userPolicy;
	
	public DeviceSaftyModel getDeviceSafty() {
		return deviceSafty;
	}

	public void setDeviceSafty(DeviceSaftyModel deviceSafty) {
		this.deviceSafty = deviceSafty;
	}

	public Map<String, Object> getDevicePolicy() {
		return devicePolicy;
	}

	public void setDevicePolicy(Map<String, Object> devicePolicy) {
		this.devicePolicy = devicePolicy;
	}

	public PolicyModel getUserPolicy() {
		return userPolicy;
	}

	public void setUserPolicy(PolicyModel userPolicy) {
		this.userPolicy = userPolicy;
	}

	public DeviceBasicInfoModel getDeviceBasicInfo() {
		return deviceBasicInfo;
	}

	public void setDeviceBasicInfo(DeviceBasicInfoModel deviceBasicInfo) {
		this.deviceBasicInfo = deviceBasicInfo;
	}

	public DeviceNetworkStatusModel getDeviceNetworkStatus() {
		return deviceNetworkStatus;
	}

	public void setDeviceNetworkStatus(DeviceNetworkStatusModel deviceNetworkStatus) {
		this.deviceNetworkStatus = deviceNetworkStatus;
	}

	public List<DeviceAppInfoModel> getDeviceAppInfoList() {
		return deviceAppInfoList;
	}

	public void setDeviceAppInfoList(List<DeviceAppInfoModel> deviceAppInfoList) {
		this.deviceAppInfoList = deviceAppInfoList;
	}
	
	
	
}

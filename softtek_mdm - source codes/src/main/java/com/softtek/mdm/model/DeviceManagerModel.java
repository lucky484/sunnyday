package com.softtek.mdm.model;

import java.util.Date;

public class DeviceManagerModel extends BaseEntity{
    
	private Integer id;
	private String userName;
	private Integer userId;
	private String udid;
	private String device_name;
	private String device_type;
	private String sn;
	private String esnoOrImei;
	private String belong;
	private Integer device_status;
	private Integer irr_status;
	private Integer lost_status;
	private Integer break_status;
	private Integer visit_status;
    private Date last_collection_time;
    private Integer isActive;
    private Float time;  //当前时间减去采集时间获得的小时数
    private String realName;
    private String name;
    private Integer userType;
    private Integer orgId;//机构的id，执行定时任务时用到
    private Float paramSetTime;//配置的托管参数的时间
    private String osVersion;
    private String phoneNumber;
    private String appVersion;
    private String ip;
    private String power;
    private String resolution;
    private String capacity;
    private String lastLoginTime;
    private String sdCard;
    private String deviceUniqueId;
    private String longitude;
    private String latitude;
    private String flux;
    private Integer enableUnbund;
    private String deviceStatus;
    private String deviceToken;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getEsnoOrImei() {
		return esnoOrImei;
	}
	public void setEsnoOrImei(String esnoOrImei) {
		this.esnoOrImei = esnoOrImei;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public Integer getDevice_status() {
		return device_status;
	}
	public void setDevice_status(Integer device_status) {
		this.device_status = device_status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIrr_status() {
		return irr_status;
	}
	public void setIrr_status(Integer irr_status) {
		this.irr_status = irr_status;
	}
	public Integer getLost_status() {
		return lost_status;
	}
	public void setLost_status(Integer lost_status) {
		this.lost_status = lost_status;
	}
	public Integer getBreak_status() {
		return break_status;
	}
	public void setBreak_status(Integer break_status) {
		this.break_status = break_status;
	}
	public Integer getVisit_status() {
		return visit_status;
	}
	public void setVisit_status(Integer visit_status) {
		this.visit_status = visit_status;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public Date getLast_collection_time() {
		return last_collection_time;
	}
	public void setLast_collection_time(Date last_collection_time) {
		this.last_collection_time = last_collection_time;
	}
	public Float getTime() {
		if(getLast_collection_time() != null){
			Float time = (float)(new Date().getTime() - getLast_collection_time().getTime())/60000;
			return time;
		} 
		return null;
	}
	public void setTime(Float time) {
		this.time = time;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Float getParamSetTime() {
		return paramSetTime;
	}
	public void setParamSetTime(Float paramSetTime) {
		this.paramSetTime = paramSetTime;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getSdCard() {
		return sdCard;
	}
	public void setSdCard(String sdCard) {
		this.sdCard = sdCard;
	}
	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}
	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getFlux() {
		return flux;
	}
	public void setFlux(String flux) {
		this.flux = flux;
	}
	public Integer getEnableUnbund() {
		return enableUnbund;
	}
	public void setEnableUnbund(Integer enableUnbund) {
		this.enableUnbund = enableUnbund;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceManagerModel [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", udid=");
		builder.append(udid);
		builder.append(", device_name=");
		builder.append(device_name);
		builder.append(", device_type=");
		builder.append(device_type);
		builder.append(", sn=");
		builder.append(sn);
		builder.append(", esnoOrImei=");
		builder.append(esnoOrImei);
		builder.append(", belong=");
		builder.append(belong);
		builder.append(", device_status=");
		builder.append(device_status);
		builder.append(", irr_status=");
		builder.append(irr_status);
		builder.append(", lost_status=");
		builder.append(lost_status);
		builder.append(", break_status=");
		builder.append(break_status);
		builder.append(", visit_status=");
		builder.append(visit_status);
		builder.append(", last_collection_time=");
		builder.append(last_collection_time);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", time=");
		builder.append(time);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", paramSetTime=");
		builder.append(paramSetTime);
		builder.append(", osVersion=");
		builder.append(osVersion);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", appVersion=");
		builder.append(appVersion);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", power=");
		builder.append(power);
		builder.append(", resolution=");
		builder.append(resolution);
		builder.append(", capacity=");
		builder.append(capacity);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", sdCard=");
		builder.append(sdCard);
		builder.append(", deviceUniqueId=");
		builder.append(deviceUniqueId);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", flux=");
		builder.append(flux);
		builder.append(", enableUnbund=");
		builder.append(enableUnbund);
		builder.append(", deviceStatus=");
		builder.append(deviceStatus);
		builder.append(", deviceToken=");
		builder.append(deviceToken);
		builder.append("]");
		return builder.toString();
	}
	
}

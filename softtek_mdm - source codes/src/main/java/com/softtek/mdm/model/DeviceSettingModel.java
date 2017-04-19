package com.softtek.mdm.model;

public class DeviceSettingModel {
   
	/**
	 * 锁定终端设备
	 */
	private Integer lockDevice;
	
	/**
	 * 消息主题
	 */
	private Integer sendMessage;
	
	/**
	 * 更新设备信息
	 */
	private Integer frashInfo;
	
	/**
	 * 定位服务
	 */
	private Integer mapService;
	
	/**
	 * 修改锁屏密码
	 */
	private Integer updatePassword;
	
	/**
	 * 修改锁屏密码
	 */
	private String lockPassword;
	
	/**
	 * 锁定终端屏幕
	 */
	private Integer lockTermin;
	
	/**
	 * 恢复出厂设置
	 */
	private Integer defaultSet;
	
	/**
	 * 清楚锁屏密码
	 */
	private Integer cleanPassword;
	
	/**
	 * 设备响铃
	 */
	private Integer deviceBell;
	
	/**
	 * 擦除企业数据
	 */
	private Integer deleteData;
	
	/**
	 * 标记设备丢失
	 */
	private Integer remarkDevice;
	
	/**
	 * 设为企业设备
	 */
	private Integer updateComDevice;
	
	/**
	 * 注销终端设备
	 */
	private Integer logOffDevice;
	
	/**
	 * 设备的唯一标识
	 */
	private String udid;
	
	/**
	 * 彻底删除设备
	 */
    private Integer removeDevice;
    
    private String sn;
    
    /**
     * 启用终端解绑
     */
    private Integer enableunbundle;
    
	public Integer getLockDevice() {
		return lockDevice;
	}

	public void setLockDevice(Integer lockDevice) {
		this.lockDevice = lockDevice;
	}

	public Integer getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(Integer sendMessage) {
		this.sendMessage = sendMessage;
	}

	public Integer getFrashInfo() {
		return frashInfo;
	}

	public void setFrashInfo(Integer frashInfo) {
		this.frashInfo = frashInfo;
	}

	public Integer getMapService() {
		return mapService;
	}

	public void setMapService(Integer mapService) {
		this.mapService = mapService;
	}

	public Integer getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(Integer updatePassword) {
		this.updatePassword = updatePassword;
	}

	public String getLockPassword() {
		return lockPassword;
	}

	public void setLockPassword(String lockPassword) {
		this.lockPassword = lockPassword;
	}

	public Integer getLockTermin() {
		return lockTermin;
	}

	public void setLockTermin(Integer lockTermin) {
		this.lockTermin = lockTermin;
	}

	public Integer getCleanPassword() {
		return cleanPassword;
	}

	public void setCleanPassword(Integer cleanPassword) {
		this.cleanPassword = cleanPassword;
	}

	public Integer getDeviceBell() {
		return deviceBell;
	}

	public void setDeviceBell(Integer deviceBell) {
		this.deviceBell = deviceBell;
	}

	public Integer getDeleteData() {
		return deleteData;
	}

	public void setDeleteData(Integer deleteData) {
		this.deleteData = deleteData;
	}

	public Integer getRemarkDevice() {
		return remarkDevice;
	}

	public void setRemarkDevice(Integer remarkDevice) {
		this.remarkDevice = remarkDevice;
	}

	public Integer getUpdateComDevice() {
		return updateComDevice;
	}

	public void setUpdateComDevice(Integer updateComDevice) {
		this.updateComDevice = updateComDevice;
	}

	public Integer getLogOffDevice() {
		return logOffDevice;
	}

	public void setLogOffDevice(Integer logOffDevice) {
		this.logOffDevice = logOffDevice;
	}

	public Integer getDefaultSet() {
		return defaultSet;
	}

	public void setDefaultSet(Integer defaultSet) {
		this.defaultSet = defaultSet;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public Integer getRemoveDevice() {
		return removeDevice;
	}

	public void setRemoveDevice(Integer removeDevice) {
		this.removeDevice = removeDevice;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}


	public Integer getEnableunbundle() {
		return enableunbundle;
	}

	public void setEnableunbundle(Integer enableunbundle) {
		this.enableunbundle = enableunbundle;
	}

	@Override
	public String toString() {
		return "DeviceSettingModel [lockDevice=" + lockDevice
				+ ", sendMessage=" + sendMessage + ", frashInfo=" + frashInfo
				+ ", mapService=" + mapService + ", updatePassword="
				+ updatePassword + ", lockPassword=" + lockPassword
				+ ", lockTermin=" + lockTermin + ", defaultSet=" + defaultSet
				+ ", cleanPassword=" + cleanPassword + ", deviceBell="
				+ deviceBell + ", deleteData=" + deleteData + ", remarkDevice="
				+ remarkDevice + ", updateComDevice=" + updateComDevice
				+ ", logOffDevice=" + logOffDevice + ", udid=" + udid
				+ ", removeDevice=" + removeDevice + "]";
	}
	
}

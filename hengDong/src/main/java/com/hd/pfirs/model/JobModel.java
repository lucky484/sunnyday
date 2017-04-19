package com.hd.pfirs.model;

import java.util.Arrays;

/**
 *  flag,tabId, photo,code,DeviceCode,collecttimestamp,islast
 *  对应接口 SendIDCardInfo，SendFaceInfo 参数列表
 * @author curry.su
 *	
 */
public class JobModel {
	/**
	 * 标记：1：t_pp_t_pp_faceinfo,对应SendFaceInfo，2：t_pp_idcardinfo,对应SendIDCardInfo
	 */
	private String  flag;
	/**
	 * 对应t_pp_t_pp_faceinfo,t_pp_idcardinfo 主键
	 */
	private Long tabId;
	/**
	 * 对应图片
	 */
	private byte[] photo;
	/**
	 * 对应t_pp_t_pp_faceinfo.faceCode,t_pp_idcardinfo.idCardCode
	 */
	private String code;
	/**
	 * 设备号
	 */
	private String deviceCode;
	private String groupCode;
	/**
	 * 前端采集时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 对应t_pp_t_pp_faceinfo.islast,flag为2时为空
	 */
	private String isLast;
	
	private String picType;
	
	private String direction;
	
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Long getTabId() {
		return tabId;
	}
	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getCollectTimeStamp() {
		return collectTimeStamp;
	}
	public void setCollectTimeStamp(String collectTimeStamp) {
		this.collectTimeStamp = collectTimeStamp;
	}
	public String getIsLast() {
		return isLast;
	}
	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	@Override
	public String toString() {
		return "JobModel [flag=" + flag + ", tabId=" + tabId + ", photo=" + Arrays.toString(photo) + ", code=" + code
				+ ", deviceCode=" + deviceCode + ", groupCode=" + groupCode + ", collectTimeStamp=" + collectTimeStamp
				+ ", isLast=" + isLast + "]";
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
}

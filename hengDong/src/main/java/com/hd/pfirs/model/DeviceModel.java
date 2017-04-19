/*
 * @Title: DeviceModel.java
 * @Package com.hd.pfirs.model
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年1月15日 下午4:19:52
 * @version V1.0
 */
package com.hd.pfirs.model;

import java.util.Date;

/**
  * 
  * @ClassName: DeviceModel
  * @Description: 采集设备信息pojo
  * @author ligang.yang@softtek.com
  * @date 2016年1月15日 下午3:45:07
  *
  */
public class DeviceModel {
	/**
	 *   auto_increment NUMBER	11
	 */
	private Long deviceId;

	/**
	 * 设备编号（唯一的）  VARCHAR2(30)
	 */
	private String deviceCode;

	/**
	 * 	NVARCHAR2	20					是		设备名称
	 */
	private String deviceName;
	/**
	 * 	NVARCHAR2	10					是		设备类型（移动；固定）
	 */
	private String deviceType;
	/**
	 * 	VARCHAR2	30					是		部署辖区ID
	 */
	private String regionCode;

	/**
	 * 	NVARCHAR2	30					是		部署地点
	 */
	private String installSite;
	/**
	 * 	DateTime	-					是		部署日期
	 */
	private Date installDate;

	/**
	 * 	DateTime(yyyy/mm/dd)	-					是		部署日期
	 */
	private String installDateStr;

	/**
	 * 	VARCHAR2	1					是		启用状态(0-启用；1-停用)
	 */
	private String enableStatus;

	/**
	 * 	VARCHAR2	1					是		运行状态（0-正常；1-异常）
	 */
	private String runningState;

	/**
	 * 	VARCHAR2	20					是		人脸设备的序列号（唯一的）
	 */
	private String faceDvNo;

	/**
	 * 	NVARCHAR2	20					是		人脸设备的设备型号
	 */
	private String faceDvModel;
	/**
	 * 	NVARCHAR2	20					是		人脸设备的镜头型号
	 */
	private String faceDvLensModel;
	/**
	 * 	NUMBER	5					是		人脸设备的采集照片间隔（帧数）
	 */
	private Integer faceDvCollInterval;
	/**
	 * 	VARCHAR2	30					是		人脸设备的IP
	 */
	private String faceDvIp;

	/**
	 * 	VARCHAR2	50					是		人脸设备的视频URL
	 */
	private String faceDvUrl;

	/**
	 * 	VARCHAR2	20					是		身份证设备的序列号（唯一的）
	 */
	private String idCardDvNo;

	/**
	 * 	VARCHAR2	20					是		视频设备（球机）的唯一标识ID
	 */
	private String videoId;

	/**
	 * 	VARCHAR2	50					是		视频设备（球机）的URL
	 */
	private String videoIp;

	/**
	 * 	CLOB	-					是		备注
	 */
	private String remarkDesc;
	/**
	 * 	VARCHAR2	1					是		删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	/**
	 * 	VARCHAR2	20					是		创建人
	 */
	private String createName;

	/**
	 * 	DateTime	-					是		创建时间
	 */
	private Date createTime;

	/**
	 * 	VARCHAR2	20					是		更新人
	 */
	private String updateName;

	/**
	 * 	DateTime	-					是		更新时间
	 */
	private Date updateTime;

	/**
	  * @return deviceId
	  */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	  * @param deviceId deviceId
	  */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	  * @return deviceCode
	  */
	public String getDeviceCode() {
		return deviceCode;
	}

	/**
	  * @param deviceCode deviceCode
	  */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	/**
	  * @return deviceName
	  */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	  * @param deviceName deviceName
	  */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	  * @return deviceType
	  */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	  * @param deviceType deviceType
	  */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	  * @return regionCode
	  */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	  * @param regionCode regionCode
	  */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	  * @return installSite
	  */
	public String getInstallSite() {
		return installSite;
	}

	/**
	  * @param installSite installSite
	  */
	public void setInstallSite(String installSite) {
		this.installSite = installSite;
	}

	/**
	  * @return installDate
	  */
	public Date getInstallDate() {
		return installDate;
	}

	/**
	  * @param installDate installDate
	  */
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	/**
	  * @return enableStatus
	  */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	  * @param enableStatus enableStatus
	  */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	/**
	  * @return runningState
	  */
	public String getRunningState() {
		return runningState;
	}

	/**
	  * @param runningState runningState
	  */
	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}

	/**
	  * @return faceDvNo
	  */
	public String getFaceDvNo() {
		return faceDvNo;
	}

	/**
	  * @param faceDvNo faceDvNo
	  */
	public void setFaceDvNo(String faceDvNo) {
		this.faceDvNo = faceDvNo;
	}

	/**
	  * @return faceDvModel
	  */
	public String getFaceDvModel() {
		return faceDvModel;
	}

	/**
	  * @param faceDvModel faceDvModel
	  */
	public void setFaceDvModel(String faceDvModel) {
		this.faceDvModel = faceDvModel;
	}

	/**
	  * @return faceDvLensModel
	  */
	public String getFaceDvLensModel() {
		return faceDvLensModel;
	}

	/**
	  * @param faceDvLensModel faceDvLensModel
	  */
	public void setFaceDvLensModel(String faceDvLensModel) {
		this.faceDvLensModel = faceDvLensModel;
	}

	public Integer getFaceDvCollInterval() {
		return faceDvCollInterval;
	}

	public void setFaceDvCollInterval(Integer faceDvCollInterval) {
		this.faceDvCollInterval = faceDvCollInterval;
	}

	/**
	  * @return faceDvIp
	  */
	public String getFaceDvIp() {
		return faceDvIp;
	}

	/**
	  * @param faceDvIp faceDvIp
	  */
	public void setFaceDvIp(String faceDvIp) {
		this.faceDvIp = faceDvIp;
	}

	/**
	  * @return faceDvUrl
	  */
	public String getFaceDvUrl() {
		return faceDvUrl;
	}

	/**
	  * @param faceDvUrl faceDvUrl
	  */
	public void setFaceDvUrl(String faceDvUrl) {
		this.faceDvUrl = faceDvUrl;
	}

	/**
	  * @return idCardDvNo
	  */
	public String getIdCardDvNo() {
		return idCardDvNo;
	}

	/**
	  * @param idCardDvNo idCardDvNo
	  */
	public void setIdCardDvNo(String idCardDvNo) {
		this.idCardDvNo = idCardDvNo;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoIp() {
		return videoIp;
	}

	public void setVideoIp(String videoIp) {
		this.videoIp = videoIp;
	}

	/**
	  * @return remarkDesc
	  */
	public String getRemarkDesc() {
		return remarkDesc;
	}

	/**
	  * @param remarkDesc remarkDesc
	  */
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	/**
	  * @return deleteStatus
	  */
	public String getDeleteStatus() {
		return deleteStatus;
	}

	/**
	  * @param deleteStatus deleteStatus
	  */
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	/**
	  * @return createName
	  */
	public String getCreateName() {
		return createName;
	}

	/**
	  * @param createName createName
	  */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	  * @return createTime
	  */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	  * @param createTime createTime
	  */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	  * @return updateName
	  */
	public String getUpdateName() {
		return updateName;
	}

	/**
	  * @param updateName updateName
	  */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	  * @return updateTime
	  */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	  * @param updateTime updateTime
	  */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getInstallDateStr() {
		return installDateStr;
	}

	public void setInstallDateStr(String installDateStr) {
		this.installDateStr = installDateStr;
	}

}

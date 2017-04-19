package com.hd.pfirs.model;

import java.util.Date;

/**
 * 设备信息model
 * 
 * @author ligang.yang
 *
 */
public class IPCModel {
	/**
	 * auto_increment
	 */
	private Long monitID;
	/**
	 * 前端本地保存时间
	 */
	private Date frontSaveTime;
	/**
	 * 前端本地保存时间时间戳
	 */
	private String frontSaveTimeStamp;
	/**
	 * 前端转发时间
	 */
	private Date frontRelayTime;
	/**
	 * 前端转发时间时间戳
	 */
	private String frontRelayTimeStamp;
	/**
	 * DateTime 30 是 汇聚保存时间
	 */
	private Date togthSaveTime;
	/**
	 * VARCHAR2 20 是 汇聚保存时间时间戳
	 */
	private String togthSaveTimeStamp;
	/**
	 * DateTime 30 是 发送时间
	 */
	private Date relayTime;
	/**
	 * VARCHAR2 20 是 发送时间戳
	 */
	private String relayTimeStamp;
	/**
	 * VARCHAR2 1 是 发送标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String relayFlag;
	/**
	 * VARCHAR2 30 是 设备编号
	 */
	private String deviceCode;
	/**
	 * NVARCHAR2 10 是 人脸设备连接状态（正常；异常）
	 */
	private String faceConnState;
	/**
	 * NVARCHAR2 10 是 身份证设备连接状态（正常；异常）
	 */
	private String IDCardConnState;
	/**
	 * VARCHAR2 5 是 内存使用率（%）
	 */
	private String memoryUsage;
	/**
	 * NVARCHAR2 20 是 硬盘空间（剩余/共有）
	 */
	private String diskSpace;
	/**
	 * NUMBER 10 0 是 数据库大小（M）
	 */
	private Long DBSize;
	/**
	 * NUMBER 10 0 是 人脸采集数量（条）
	 */
	private Long faceCollectNum;
	/**
	 * NUMBER 10 0 是 人脸已发送数量（条）
	 */
	private Long faceRelayNum;
	/**
	 * NUMBER 10 0 是 身份证采集数量（条）
	 */
	private Long IDCardCollectNum;
	/**
	 * NUMBER 10 0 是 身份证已发送数量（条）
	 */
	private Long IDCardRelayNum;
	/**
	 * CLOB - 是 备注
	 */
	private String remarkDesc;
	/**
	 * VARCHAR2 1 是 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;
	/**
	 * VARCHAR2 20 是 创建人
	 */
	private String createName;
	/**
	 * DateTime - 是 创建时间
	 */
	private Date createTime;
	/**
	 * VARCHAR2 20 是 更新人
	 */
	private String updateName;
	/**
	 * DateTime - 是 更新时间
	 */
	private Date updateTime;

	/**
	 * 设备类型
	 */
	private String deviceType;

	/**
	 * 采集地点
	 */
	private String collectSite;

	/**
	 * 启用状态
	 */
	private String enableStatus;

	/**
	 * @return the monitID
	 */
	public Long getMonitID() {
		return monitID;
	}

	/**
	 * @param monitID
	 *            the monitID to set
	 */
	public void setMonitID(Long monitID) {
		this.monitID = monitID;
	}

	/**
	 * @return the frontSaveTime
	 */
	public Date getFrontSaveTime() {
		return frontSaveTime;
	}

	/**
	 * @param frontSaveTime
	 *            the frontSaveTime to set
	 */
	public void setFrontSaveTime(Date frontSaveTime) {
		this.frontSaveTime = frontSaveTime;
	}

	/**
	 * @return the frontSaveTimeStamp
	 */
	public String getFrontSaveTimeStamp() {
		return frontSaveTimeStamp;
	}

	/**
	 * @param frontSaveTimeStamp
	 *            the frontSaveTimeStamp to set
	 */
	public void setFrontSaveTimeStamp(String frontSaveTimeStamp) {
		this.frontSaveTimeStamp = frontSaveTimeStamp;
	}

	/**
	 * @return the frontRelayTime
	 */
	public Date getFrontRelayTime() {
		return frontRelayTime;
	}

	/**
	 * @param frontRelayTime
	 *            the frontRelayTime to set
	 */
	public void setFrontRelayTime(Date frontRelayTime) {
		this.frontRelayTime = frontRelayTime;
	}

	/**
	 * @return the frontRelayTimeStamp
	 */
	public String getFrontRelayTimeStamp() {
		return frontRelayTimeStamp;
	}

	/**
	 * @param frontRelayTimeStamp
	 *            the frontRelayTimeStamp to set
	 */
	public void setFrontRelayTimeStamp(String frontRelayTimeStamp) {
		this.frontRelayTimeStamp = frontRelayTimeStamp;
	}

	/**
	 * @return the togthSaveTime
	 */
	public Date getTogthSaveTime() {
		return togthSaveTime;
	}

	/**
	 * @param togthSaveTime
	 *            the togthSaveTime to set
	 */
	public void setTogthSaveTime(Date togthSaveTime) {
		this.togthSaveTime = togthSaveTime;
	}

	/**
	 * @return the togthSaveTimeStamp
	 */
	public String getTogthSaveTimeStamp() {
		return togthSaveTimeStamp;
	}

	/**
	 * @param togthSaveTimeStamp
	 *            the togthSaveTimeStamp to set
	 */
	public void setTogthSaveTimeStamp(String togthSaveTimeStamp) {
		this.togthSaveTimeStamp = togthSaveTimeStamp;
	}

	/**
	 * @return the relayTime
	 */
	public Date getRelayTime() {
		return relayTime;
	}

	/**
	 * @param relayTime
	 *            the relayTime to set
	 */
	public void setRelayTime(Date relayTime) {
		this.relayTime = relayTime;
	}

	/**
	 * @return the relayTimeStamp
	 */
	public String getRelayTimeStamp() {
		return relayTimeStamp;
	}

	/**
	 * @param relayTimeStamp
	 *            the relayTimeStamp to set
	 */
	public void setRelayTimeStamp(String relayTimeStamp) {
		this.relayTimeStamp = relayTimeStamp;
	}

	/**
	 * @return the relayFlag
	 */
	public String getRelayFlag() {
		return relayFlag;
	}

	/**
	 * @param relayFlag
	 *            the relayFlag to set
	 */
	public void setRelayFlag(String relayFlag) {
		this.relayFlag = relayFlag;
	}

	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}

	/**
	 * @param deviceCode
	 *            the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	/**
	 * @return the faceConnState
	 */
	public String getFaceConnState() {
		return faceConnState;
	}

	/**
	 * @param faceConnState
	 *            the faceConnState to set
	 */
	public void setFaceConnState(String faceConnState) {
		this.faceConnState = faceConnState;
	}

	/**
	 * @return the iDCardConnState
	 */
	public String getIDCardConnState() {
		return IDCardConnState;
	}

	/**
	 * @param iDCardConnState
	 *            the iDCardConnState to set
	 */
	public void setIDCardConnState(String iDCardConnState) {
		IDCardConnState = iDCardConnState;
	}

	/**
	 * @return the memoryUsage
	 */
	public String getMemoryUsage() {
		return memoryUsage;
	}

	/**
	 * @param memoryUsage
	 *            the memoryUsage to set
	 */
	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	/**
	 * @return the diskSpace
	 */
	public String getDiskSpace() {
		return diskSpace;
	}

	/**
	 * @param diskSpace
	 *            the diskSpace to set
	 */
	public void setDiskSpace(String diskSpace) {
		this.diskSpace = diskSpace;
	}

	/**
	 * @return the dBSize
	 */
	public Long getDBSize() {
		return DBSize;
	}

	/**
	 * @param dBSize
	 *            the dBSize to set
	 */
	public void setDBSize(Long dBSize) {
		DBSize = dBSize;
	}

	/**
	 * @return the faceCollectNum
	 */
	public Long getFaceCollectNum() {
		return faceCollectNum;
	}

	/**
	 * @param faceCollectNum
	 *            the faceCollectNum to set
	 */
	public void setFaceCollectNum(Long faceCollectNum) {
		this.faceCollectNum = faceCollectNum;
	}

	/**
	 * @return the faceRelayNum
	 */
	public Long getFaceRelayNum() {
		return faceRelayNum;
	}

	/**
	 * @param faceRelayNum
	 *            the faceRelayNum to set
	 */
	public void setFaceRelayNum(Long faceRelayNum) {
		this.faceRelayNum = faceRelayNum;
	}

	/**
	 * @return the iDCardCollectNum
	 */
	public Long getIDCardCollectNum() {
		return IDCardCollectNum;
	}

	/**
	 * @param iDCardCollectNum
	 *            the iDCardCollectNum to set
	 */
	public void setIDCardCollectNum(Long iDCardCollectNum) {
		IDCardCollectNum = iDCardCollectNum;
	}

	/**
	 * @return the iDCardRelayNum
	 */
	public Long getIDCardRelayNum() {
		return IDCardRelayNum;
	}

	/**
	 * @param iDCardRelayNum
	 *            the iDCardRelayNum to set
	 */
	public void setIDCardRelayNum(Long iDCardRelayNum) {
		IDCardRelayNum = iDCardRelayNum;
	}

	/**
	 * @return the remarkDesc
	 */
	public String getRemarkDesc() {
		return remarkDesc;
	}

	/**
	 * @param remarkDesc
	 *            the remarkDesc to set
	 */
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	/**
	 * @return the deleteStatus
	 */
	public String getDeleteStatus() {
		return deleteStatus;
	}

	/**
	 * @param deleteStatus
	 *            the deleteStatus to set
	 */
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}

	/**
	 * @param updateName
	 *            the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the collectSite
	 */
	public String getCollectSite() {
		return collectSite;
	}

	/**
	 * @param collectSite the collectSite to set
	 */
	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}

	/**
	 * @return the enableStatus
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * @param enableStatus the enableStatus to set
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

}

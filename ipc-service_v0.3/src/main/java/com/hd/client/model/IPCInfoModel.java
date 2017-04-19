package com.hd.client.model;

/**
 * 采集端设备的信息类
 * 
 * @author ligang.yang
 *
 */
public class IPCInfoModel {
	/**
	 * 设备编号
	 */
	private String deviceCode;
	/**
	 * 人脸设备连接状态（正常；异常）
	 */
	private String faceConnState;
	/**
	 * 身份证设备连接状态（正常；异常）
	 */
	private String idCardConnState;

	/**
	 * 内存使用率（%）
	 */
	private String memoryUsage;

	/**
	 * 硬盘空间（剩余/共有）
	 */
	private String diskSpace;

	/**
	 * 数据库大小（M）
	 */
	private Integer dbSize;

	/**
	 * 人脸采集数量
	 */
	private Integer faceCollectNum;

	/**
	 * 人脸已发送数量（条）
	 */
	private Integer faceRelayNum;

	/**
	 * 身份证采集数量（条）
	 */
	private Integer idCardCollectNum;
	/**
	 * 身份证已发送数量（条）
	 */
	private Integer idCardRelayNum;

	/**
	 * 转发时间戳
	 */
	private String relayTimeStamp;

	/**
	 * 转发标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String relayFlag;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 更新人
	 */
	private String updateName;

	/**
	 * 用于区分表
	 */
	private String f_type;

	public IPCInfoModel() {
		super();
	}

	public IPCInfoModel(String deviceCode, String faceConnState, String idCardConnState, String memoryUsage,
			String diskSpace, /* M */Integer dbSize, Integer faceCollectNum, Integer faceRelayNum,
			Integer idCardCollectNum, Integer idCardRelayNum, String relayTimeStamp, String relayFlag,
			String remarkDesc, String deleteStatus, String createName, String updateName) {
		super();
		this.deviceCode = deviceCode;
		this.faceConnState = faceConnState;
		this.idCardConnState = idCardConnState;
		this.memoryUsage = memoryUsage;
		this.diskSpace = diskSpace;
		this.dbSize = dbSize;
		this.faceCollectNum = faceCollectNum;
		this.faceRelayNum = faceRelayNum;
		this.idCardCollectNum = idCardCollectNum;
		this.idCardRelayNum = idCardRelayNum;
		this.relayTimeStamp = relayTimeStamp;
		this.relayFlag = relayFlag;
		this.remarkDesc = remarkDesc;
		this.deleteStatus = deleteStatus;
		this.createName = createName;
		this.updateName = updateName;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getFaceConnState() {
		return faceConnState;
	}

	public void setFaceConnState(String faceConnState) {
		this.faceConnState = faceConnState;
	}

	public String getIdCardConnState() {
		return idCardConnState;
	}

	public void setIdCardConnState(String idCardConnState) {
		this.idCardConnState = idCardConnState;
	}

	public String getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public String getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(String diskSpace) {
		this.diskSpace = diskSpace;
	}

	public Integer getDbSize() {
		return dbSize;
	}

	public void setDbSize(Integer dbSize) {
		this.dbSize = dbSize;
	}

	public Integer getFaceCollectNum() {
		return faceCollectNum;
	}

	public void setFaceCollectNum(Integer faceCollectNum) {
		this.faceCollectNum = faceCollectNum;
	}

	public Integer getFaceRelayNum() {
		return faceRelayNum;
	}

	public void setFaceRelayNum(Integer faceRelayNum) {
		this.faceRelayNum = faceRelayNum;
	}

	public Integer getIdCardCollectNum() {
		return idCardCollectNum;
	}

	public void setIdCardCollectNum(Integer idCardCollectNum) {
		this.idCardCollectNum = idCardCollectNum;
	}

	public Integer getIdCardRelayNum() {
		return idCardRelayNum;
	}

	public void setIdCardRelayNum(Integer idCardRelayNum) {
		this.idCardRelayNum = idCardRelayNum;
	}

	public String getRelayTimeStamp() {
		return relayTimeStamp;
	}

	public void setRelayTimeStamp(String relayTimeStamp) {
		this.relayTimeStamp = relayTimeStamp;
	}

	public String getRelayFlag() {
		return relayFlag;
	}

	public void setRelayFlag(String relayFlag) {
		this.relayFlag = relayFlag;
	}

	public String getRemarkDesc() {
		return remarkDesc;
	}

	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getF_type() {
		return f_type;
	}

	public void setF_type(String f_type) {
		this.f_type = f_type;
	}

	@Override
	public String toString() {
		return "IPCInfoModel [deviceCode=" + deviceCode + ", faceConnState=" + faceConnState + ", idCardConnState="
				+ idCardConnState + ", memoryUsage=" + memoryUsage + ", diskSpace=" + diskSpace + ", dbSize=" + dbSize
				+ ", faceCollectNum=" + faceCollectNum + ", faceRelayNum=" + faceRelayNum + ", idCardCollectNum="
				+ idCardCollectNum + ", idCardRelayNum=" + idCardRelayNum + ", relayTimeStamp=" + relayTimeStamp
				+ ", relayFlag=" + relayFlag + ", remarkDesc=" + remarkDesc + ", deleteStatus=" + deleteStatus
				+ ", createName=" + createName + ", updateName=" + updateName + "]";
	}

}

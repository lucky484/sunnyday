package com.hd.pfirs.model;

/**
 * 表名：T_SS_ParamSet（参数配置表）
 * 
 * @author ligang.yang
 *
 */
public class ParamSet {

	/**
	 * id
	 */
	private Long paramId;

	/**
	 * 缉控人员特征库同步频率（小时）
	 */
	private Integer ctrlSyncCycle;

	/**
	 * 全量人员特征库同步频率（小时）
	 */
	private Integer totalSyncCycle;

	/**
	 * 人证合一比对的相似度的预警阈值
	 */
	private Integer faceCardCompAlarmVal;

	/**
	 * 人脸比对的相似度的预警阈值
	 */
	private Integer faceCompAlarmVal;

	/**
	 * 人脸比对启停开关（1-启用；0-关闭）
	 */
	private String faceCompareFlag;

	/**
	 * 身份证比对启停开关 （1-启用；0-关闭）
	 */
	private String cardCompareFlag;

	/**
	 * 人证合一比对启停开关（1-启用；0-关闭）
	 */
	private String faceCardCompFlag;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	/**
	 * @return the ctrlSyncCycle
	 */
	public Integer getCtrlSyncCycle() {
		return ctrlSyncCycle;
	}

	/**
	 * @param ctrlSyncCycle
	 *            the ctrlSyncCycle to set
	 */
	public void setCtrlSyncCycle(Integer ctrlSyncCycle) {
		this.ctrlSyncCycle = ctrlSyncCycle;
	}

	/**
	 * @return the totalSyncCycle
	 */
	public Integer getTotalSyncCycle() {
		return totalSyncCycle;
	}

	/**
	 * @param totalSyncCycle
	 *            the totalSyncCycle to set
	 */
	public void setTotalSyncCycle(Integer totalSyncCycle) {
		this.totalSyncCycle = totalSyncCycle;
	}

	/**
	 * @return the faceCardCompAlarmVal
	 */
	public Integer getFaceCardCompAlarmVal() {
		return faceCardCompAlarmVal;
	}

	/**
	 * @param faceCardCompAlarmVal
	 *            the faceCardCompAlarmVal to set
	 */
	public void setFaceCardCompAlarmVal(Integer faceCardCompAlarmVal) {
		this.faceCardCompAlarmVal = faceCardCompAlarmVal;
	}

	/**
	 * @return the faceCompareFlag
	 */
	public String getFaceCompareFlag() {
		return faceCompareFlag;
	}

	/**
	 * @param faceCompareFlag
	 *            the faceCompareFlag to set
	 */
	public void setFaceCompareFlag(String faceCompareFlag) {
		this.faceCompareFlag = faceCompareFlag;
	}

	/**
	 * @return the cardCompareFlag
	 */
	public String getCardCompareFlag() {
		return cardCompareFlag;
	}

	/**
	 * @param cardCompareFlag
	 *            the cardCompareFlag to set
	 */
	public void setCardCompareFlag(String cardCompareFlag) {
		this.cardCompareFlag = cardCompareFlag;
	}

	/**
	 * @return the faceCardCompFlag
	 */
	public String getFaceCardCompFlag() {
		return faceCardCompFlag;
	}

	/**
	 * @param faceCardCompFlag
	 *            the faceCardCompFlag to set
	 */
	public void setFaceCardCompFlag(String faceCardCompFlag) {
		this.faceCardCompFlag = faceCardCompFlag;
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
	 * @return the paramId
	 */
	public Long getParamId() {
		return paramId;
	}

	/**
	 * @param paramId
	 *            the paramId to set
	 */
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	/**
	 * @return the faceCompAlarmVal
	 */
	public Integer getFaceCompAlarmVal() {
		return faceCompAlarmVal;
	}

	/**
	 * @param faceCompAlarmVal
	 *            the faceCompAlarmVal to set
	 */
	public void setFaceCompAlarmVal(Integer faceCompAlarmVal) {
		this.faceCompAlarmVal = faceCompAlarmVal;
	}

	@Override
	public String toString() {
		return "paramId=" + paramId + ", ctrlSyncCycle=" + ctrlSyncCycle + ", totalSyncCycle=" + totalSyncCycle
				+ ", faceCardCompAlarmVal=" + faceCardCompAlarmVal + ", faceCompAlarmVal=" + faceCompAlarmVal
				+ ", faceCompareFlag=" + faceCompareFlag + ", cardCompareFlag=" + cardCompareFlag
				+ ", faceCardCompFlag=" + faceCardCompFlag + ", remarkDesc=" + remarkDesc + ", deleteStatus="
				+ deleteStatus;
	}

}

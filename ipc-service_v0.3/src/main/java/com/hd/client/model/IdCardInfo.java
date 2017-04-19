package com.hd.client.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class IdCardInfo {

	/**
	 * auto_increment
	 */
	private Long cardId;
	/**
	 * 编号（设备id_时间戳_顺番）
	 */
	private String cardCode;
	/**
	 * 采集时间时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 采集地点
	 */
	private String collectSite;
	/**
	 * 身份证照片
	 */
	private byte[] idCardPic;

	private String bmpPath;

	private String bmpData;
	/**
	 * 姓名
	 */
	private String idCardName;
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 性别
	 */
	private String idCardSex;
	/**
	 * 生日
	 */
	private String idCardBirth;
	/**
	 * 民族
	 */
	private String idCardNation;
	/**
	 * 地址
	 */
	private String idCardAddress;
	/**
	 * 发证单位
	 */
	private String cardUnit;
	/**
	 * 发证日
	 */
	private String cardIssueDate;
	/**
	 * 截止日
	 */
	private String cardExpiryDate;
	/**
	 * 身份证16位ID
	 */
	private String cardID16;

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
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新人
	 */
	private String updateName;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 用于区分表
	 */
	private String f_type;

	public IdCardInfo() {
		super();
	}

	public IdCardInfo(Long cardId, String cardCode, String collectTimeStamp, String collectSite, byte[] idCardPic,
			String idCardName, String idCardNo, String idCardSex, String idCardBirth, String idCardNation,
			String idCardAddress, String cardUnit, String cardIssueDate, String cardExpiryDate, String cardID16,
			Timestamp relayTime, String relayFlag, String remarkDesc, String deleteStatus, String createName,
			String createTime, String updateName, String updateTime, String bmpPath, String bmpData) {
		super();
		this.cardId = cardId;
		this.cardCode = cardCode;
		this.collectTimeStamp = collectTimeStamp;
		this.collectSite = collectSite;
		this.idCardPic = idCardPic;
		this.idCardName = idCardName;
		this.idCardNo = idCardNo;
		this.idCardSex = idCardSex;
		this.idCardBirth = idCardBirth;
		this.idCardNation = idCardNation;
		this.idCardAddress = idCardAddress;
		this.cardUnit = cardUnit;
		this.cardIssueDate = cardIssueDate;
		this.cardExpiryDate = cardExpiryDate;
		this.cardID16 = cardID16;
		this.relayFlag = relayFlag;
		this.remarkDesc = remarkDesc;
		this.deleteStatus = deleteStatus;
		this.createName = createName;
		this.createTime = createTime;
		this.updateName = updateName;
		this.updateTime = updateTime;
		this.bmpPath = bmpPath;
		this.bmpData = bmpData;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the cardId
	 */
	public Long getCardId() {
		return cardId;
	}

	/**
	 * @return the cardCode
	 */
	public String getCardCode() {
		return cardCode;
	}

	/**
	 * @param cardCode
	 *            the cardCode to set
	 */
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	/**
	 * @return the collectTimeStamp
	 */
	public String getCollectTimeStamp() {
		return collectTimeStamp;
	}

	/**
	 * @param collectTimeStamp
	 *            the collectTimeStamp to set
	 */
	public void setCollectTimeStamp(String collectTimeStamp) {
		this.collectTimeStamp = collectTimeStamp;
	}

	/**
	 * @return the collectSite
	 */
	public String getCollectSite() {
		return collectSite;
	}

	/**
	 * @param collectSite
	 *            the collectSite to set
	 */
	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}

	/**
	 * @return the idCardPic
	 */
	public byte[] getIdCardPic() {
		return idCardPic;
	}

	/**
	 * @param idCardPic
	 *            the idCardPic to set
	 */
	public void setIdCardPic(byte[] idCardPic) {
		this.idCardPic = idCardPic;
	}

	/**
	 * @return the idCardName
	 */
	public String getIdCardName() {
		return idCardName;
	}

	/**
	 * @param idCardName
	 *            the idCardName to set
	 */
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}

	/**
	 * @return the idCardNo
	 */
	public String getIdCardNo() {
		return idCardNo;
	}

	/**
	 * @param idCardNo
	 *            the idCardNo to set
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	/**
	 * @return the idCardSex
	 */
	public String getIdCardSex() {
		return idCardSex;
	}

	/**
	 * @param idCardSex
	 *            the idCardSex to set
	 */
	public void setIdCardSex(String idCardSex) {
		this.idCardSex = idCardSex;
	}

	/**
	 * @return the idCardBirth
	 */
	public String getIdCardBirth() {
		return idCardBirth;
	}

	/**
	 * @param idCardBirth
	 *            the idCardBirth to set
	 */
	public void setIdCardBirth(String idCardBirth) {
		this.idCardBirth = idCardBirth;
	}

	/**
	 * @return the idCardNation
	 */
	public String getIdCardNation() {
		return idCardNation;
	}

	/**
	 * @param idCardNation
	 *            the idCardNation to set
	 */
	public void setIdCardNation(String idCardNation) {
		this.idCardNation = idCardNation;
	}

	/**
	 * @return the idCardAddress
	 */
	public String getIdCardAddress() {
		return idCardAddress;
	}

	/**
	 * @param idCardAddress
	 *            the idCardAddress to set
	 */
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}

	/**
	 * @return the cardUnit
	 */
	public String getCardUnit() {
		return cardUnit;
	}

	/**
	 * @param cardUnit
	 *            the cardUnit to set
	 */
	public void setCardUnit(String cardUnit) {
		this.cardUnit = cardUnit;
	}

	/**
	 * @return the cardIssueDate
	 */
	public String getCardIssueDate() {
		return cardIssueDate;
	}

	/**
	 * @param cardIssueDate
	 *            the cardIssueDate to set
	 */
	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}

	/**
	 * @return the cardExpiryDate
	 */
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	/**
	 * @param cardExpiryDate
	 *            the cardExpiryDate to set
	 */
	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	/**
	 * @return the cardID16
	 */
	public String getCardID16() {
		return cardID16;
	}

	/**
	 * @param cardID16
	 *            the cardID16 to set
	 */
	public void setCardID16(String cardID16) {
		this.cardID16 = cardID16;
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
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
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
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the bmpPath
	 */
	public String getBmpPath() {
		return bmpPath;
	}

	/**
	 * @param bmpPath
	 *            the bmpPath to set
	 */
	public void setBmpPath(String bmpPath) {
		this.bmpPath = bmpPath;
	}

	public String getF_type() {
		return f_type;
	}

	public void setF_type(String f_type) {
		this.f_type = f_type;
	}

	/**
	 * @return the bmpData
	 */
	public String getBmpData() {
		return bmpData;
	}

	/**
	 * @param bmpData the bmpData to set
	 */
	public void setBmpData(String bmpData) {
		this.bmpData = bmpData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IdCardInfo [cardId=" + cardId + ", cardCode=" + cardCode + ", collectTimeStamp=" + collectTimeStamp
				+ ", collectSite=" + collectSite + ", idCardPic=" + Arrays.toString(idCardPic) + ", bmpPath=" + bmpPath
				+ ", idCardName=" + idCardName + ", idCardNo=" + idCardNo + ", idCardSex=" + idCardSex
				+ ", idCardBirth=" + idCardBirth + ", idCardNation=" + idCardNation + ", idCardAddress=" + idCardAddress
				+ ", cardUnit=" + cardUnit + ", cardIssueDate=" + cardIssueDate + ", cardExpiryDate=" + cardExpiryDate
				+ ", cardID16=" + cardID16 + ", relayFlag=" + relayFlag + ", remarkDesc=" + remarkDesc
				+ ", deleteStatus=" + deleteStatus + ", createName=" + createName + ", createTime=" + createTime
				+ ", updateName=" + updateName + ", updateTime=" + updateTime + "]" + bmpData;
	}

}

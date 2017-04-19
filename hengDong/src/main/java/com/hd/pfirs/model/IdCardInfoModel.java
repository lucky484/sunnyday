package com.hd.pfirs.model;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 
 * @author cliff.fan
 *
 */
public class IdCardInfoModel {

	/**
	 * auto_increment
	 */
	private Long cardId;
	/**
	 * 编号（对应前端身份证采集信息表里的编号）
	 */
	private String cardCode;
	/**
	 * 采集时间
	 */
	private Timestamp collectTime;

	/**
	 * 采集时间时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 前端本地保存时间
	 */
	private Timestamp frontSaveTime;
	/**
	 * 前端本地保存时间时间戳
	 */
	private String frontSaveTimeStamp;
	/**
	 * 前端转发时间
	 */
	private Timestamp frontRelayTime;
	/**
	 * 前端转发时间时间戳
	 */
	private String frontRelayTimeStamp;
	/**
	 * 汇聚保存时间
	 */
	private Timestamp togthSaveTime;
	/**
	 * 汇聚保存时间时间戳
	 */
	private String togthSaveTimeStamp;
	/**
	 * 发送时间
	 */
	private Timestamp relayTime;
	/**
	 * 发送时间戳
	 */
	private String relayTimeStamp;
	/**
	 * 发送标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String relayFlag;
	/**
	 * 设备编号
	 */
	private String deviceCode;
	/**
	 * 辖区（后台数据库有）
	 */
	private String regionCode;
	/**
	 * 采集地点
	 */
	private String collectSite;
	/**
	 * 身份证照片
	 */
	private byte[] idCardPic;
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
	 * 民族
	 */
	private String idCardNation;
	/**
	 * 生日
	 */
	private String idCardBirth;
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
	 * 备注clob
	 */
	private String remarkDesc;
	/**
	 * 转发标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String deleteStatus;
	/**
	 * 创建人
	 */
	private String createName;
	/**
	 * 创建时间datetime
	 */
	private String createTime;
	/**
	 * 更新人
	 */
	private String updateName;

	/**
	 * 更新时间datetime
	 */
	private String updateTime;

	/**
	 * @return the cardId
	 */
	public Long getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(Long cardId) {
		this.cardId = cardId;
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
	 * @param collectTime
	 *            the collectTime to set
	 */
	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}

	/**
	 * @return the collectTime
	 */
	public Timestamp getCollectTime() {
		return collectTime;
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
	 * @return the frontSaveTime
	 */
	public Timestamp getFrontSaveTime() {
		return frontSaveTime;
	}

	/**
	 * @param frontSaveTime
	 *            the frontSaveTime to set
	 */
	public void setFrontSaveTime(Timestamp frontSaveTime) {
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
	public Timestamp getFrontRelayTime() {
		return frontRelayTime;
	}

	/**
	 * @param frontRelayTime
	 *            the frontRelayTime to set
	 */
	public void setFrontRelayTime(Timestamp frontRelayTime) {
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
	public Timestamp getTogthSaveTime() {
		return togthSaveTime;
	}

	/**
	 * @param togthSaveTime
	 *            the togthSaveTime to set
	 */
	public void setTogthSaveTime(Timestamp togthSaveTime) {
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
	public Timestamp getRelayTime() {
		return relayTime;
	}

	/**
	 * @param relayTime
	 *            the relayTime to set
	 */
	public void setRelayTime(Timestamp relayTime) {
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
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IdCardInfoModel [cardId=" + cardId + ", cardCode=" + cardCode + ", collectTime=" + collectTime
				+ ", collectTimeStamp=" + collectTimeStamp + ", frontSaveTime=" + frontSaveTime
				+ ", frontSaveTimeStamp=" + frontSaveTimeStamp + ", frontRelayTime=" + frontRelayTime
				+ ", frontRelayTimeStamp=" + frontRelayTimeStamp + ", togthSaveTime=" + togthSaveTime
				+ ", togthSaveTimeStamp=" + togthSaveTimeStamp + ", relayTime=" + relayTime + ", relayTimeStamp="
				+ relayTimeStamp + ", relayFlag=" + relayFlag + ", deviceCode=" + deviceCode + ", regionCode="
				+ regionCode + ", collectSite=" + collectSite + ", idCardPic=" + Arrays.toString(idCardPic)
				+ ", idCardName=" + idCardName + ", idCardNo=" + idCardNo + ", idCardSex=" + idCardSex
				+ ", idCardNation=" + idCardNation + ", idCardBirth=" + idCardBirth + ", idCardAddress=" + idCardAddress
				+ ", cardUnit=" + cardUnit + ", cardIssueDate=" + cardIssueDate + ", cardExpiryDate=" + cardExpiryDate
				+ ", cardID16=" + cardID16 + ", remarkDesc=" + remarkDesc + ", deleteStatus=" + deleteStatus
				+ ", createName=" + createName + ", createTime=" + createTime + ", updateName=" + updateName
				+ ", updateTime=" + updateTime + "]";
	}

}

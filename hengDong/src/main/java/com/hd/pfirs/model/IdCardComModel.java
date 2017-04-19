package com.hd.pfirs.model;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 身份证比对的对应类
 * 
 * @author ligang.yang
 *
 */
public class IdCardComModel {
	/**
	 * 对比
	 */
	private Long cardComprltId;

	/**
	 * 结果接收时间
	 */
	private Timestamp receiveTime;

	/**
	 * 结果接收时间戳
	 */
	private String receiveTimeStamp;

	/**
	 * 身份证采集数据编号（对应身份证采集信息表的编号）
	 */
	private String cardCode;

	/**
	 * 缉控库ID
	 */
	private String ctrlBaseID;
	/**
	 * 服务器比对图片
	 */
	private byte[] photo;

	/**
	 * 库ID
	 */
	private String compareBaseID;

	/**
	 * 缉控数据库对比时间
	 */
	private Timestamp relayTime;
	/**
	 * 缉控数据库对比时间戳
	 */
	private String relayTimeStamp;

	/**
	 * 发送标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String relayFlag;
	/**
	 * 是否布控人员/是否比中(0-否；1-是)
	 */
	private String isControlled;
	/**
	 * 采集身份证号
	 */
	private String collectIDCardNo;
	/**
	 * 采集姓名
	 */
	private String collectIDCardName;
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
	 * 显示标记(0:未推送:表示布控人员, 1:已推送：非布控人员)
	 */
	private String flag;

	/**
	 * @return the cardComprltId
	 */
	public Long getCardComprltId() {
		return cardComprltId;
	}

	/**
	 * @param cardComprltId
	 *            the cardComprltId to set
	 */
	public void setCardComprltId(Long cardComprltId) {
		this.cardComprltId = cardComprltId;
	}

	/**
	 * @return the receiveTime
	 */
	public Timestamp getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime
	 *            the receiveTime to set
	 */
	public void setReceiveTime(Timestamp receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return the receiveTimeStamp
	 */
	public String getReceiveTimeStamp() {
		return receiveTimeStamp;
	}

	/**
	 * @param receiveTimeStamp
	 *            the receiveTimeStamp to set
	 */
	public void setReceiveTimeStamp(String receiveTimeStamp) {
		this.receiveTimeStamp = receiveTimeStamp;
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
	 * @return the ctrlBaseID
	 */
	public String getCtrlBaseID() {
		return ctrlBaseID;
	}

	/**
	 * @param ctrlBaseID
	 *            the ctrlBaseID to set
	 */
	public void setCtrlBaseID(String ctrlBaseID) {
		this.ctrlBaseID = ctrlBaseID;
	}

	/**
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/**
	 * @return the compareBaseID
	 */
	public String getCompareBaseID() {
		return compareBaseID;
	}

	/**
	 * @param compareBaseID
	 *            the compareBaseID to set
	 */
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
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
	 * @return the isControlled
	 */
	public String getIsControlled() {
		return isControlled;
	}

	/**
	 * @param isControlled
	 *            the isControlled to set
	 */
	public void setIsControlled(String isControlled) {
		this.isControlled = isControlled;
	}

	/**
	 * @return the collectIDCardNo
	 */
	public String getCollectIDCardNo() {
		return collectIDCardNo;
	}

	/**
	 * @param collectIDCardNo
	 *            the collectIDCardNo to set
	 */
	public void setCollectIDCardNo(String collectIDCardNo) {
		this.collectIDCardNo = collectIDCardNo;
	}

	/**
	 * @return the collectIDCardName
	 */
	public String getCollectIDCardName() {
		return collectIDCardName;
	}

	/**
	 * @param collectIDCardName
	 *            the collectIDCardName to set
	 */
	public void setCollectIDCardName(String collectIDCardName) {
		this.collectIDCardName = collectIDCardName;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IdCardComModel [cardComprltId=" + cardComprltId + ", receiveTime=" + receiveTime + ", receiveTimeStamp="
				+ receiveTimeStamp + ", cardCode=" + cardCode + ", ctrlBaseID=" + ctrlBaseID + ", photo="
				+ Arrays.toString(photo) + ", compareBaseID=" + compareBaseID + ", relayTime=" + relayTime
				+ ", relayTimeStamp=" + relayTimeStamp + ", relayFlag=" + relayFlag + ", isControlled=" + isControlled
				+ ", collectIDCardNo=" + collectIDCardNo + ", collectIDCardName=" + collectIDCardName + ", remarkDesc="
				+ remarkDesc + ", deleteStatus=" + deleteStatus + ", createName=" + createName + ", createTime="
				+ createTime + ", updateName=" + updateName + ", updateTime=" + updateTime + "]";
	}

}

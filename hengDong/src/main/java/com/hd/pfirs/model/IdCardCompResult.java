package com.hd.pfirs.model;

import java.util.Date;

public class IdCardCompResult {
	private long cardCompRltID; // auto_increment
	private Date receiveTime; // 结果接收时间
	private String receiveTimeStamp; // 结果接收时间戳
	private String cardCode; // 身份证采集数据编号（对应身份证采集信息表的编号）
	private String ctrlBaseID; // 缉控库ID
	private String isControlled; // 是否布控人员/是否比中(0-否；1-是)
	private String collectIDCardNo; // 采集身份证号
	private String collectIDCardName; // 采集姓名
	private String remarkDesc; // 备注
	private String deleteStatus; // 删除状态
	private String createName; // 创建人
	private Date createTime; // 创建时间
	private String updateName; // 修改人
	private Date updateTime; // 修改时间

	/**
	 * @return the cardCompRltID
	 */
	public long getCardCompRltID() {
		return cardCompRltID;
	}

	/**
	 * @param cardCompRltID
	 *            the cardCompRltID to set
	 */
	public void setCardCompRltID(long cardCompRltID) {
		this.cardCompRltID = cardCompRltID;
	}

	/**
	 * @return the receiveTime
	 */
	public Date getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime
	 *            the receiveTime to set
	 */
	public void setReceiveTime(Date receiveTime) {
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

}

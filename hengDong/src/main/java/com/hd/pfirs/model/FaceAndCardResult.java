package com.hd.pfirs.model;

import java.util.Date;

public class FaceAndCardResult {
    
	private Long ciID;    //主键ID
	private Date receiveTime; //接受时间
	private String receiveTimeStamp; //接受时间戳
	private String cardCode;     //身份证采集数据编号
	private String faceCode;     //人脸采集数据编号
	private Float similarity;   //相似度
	private String remarkDesc;    //备注
	private String deleteStatus;   //删除状态
	private String createName;     //创建人
	private Date createTime;       //创建时间
	private String updateName;     //修改人
	private Date updateTime;      //修改时间
	private String deviceCode;    //设备编号
	public Long getCiID() {
		return ciID;
	}
	public void setCiID(Long ciID) {
		this.ciID = ciID;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getReceiveTimeStamp() {
		return receiveTimeStamp;
	}
	public void setReceiveTimeStamp(String receiveTimeStamp) {
		this.receiveTimeStamp = receiveTimeStamp;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getFaceCode() {
		return faceCode;
	}
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}
	public Float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
}

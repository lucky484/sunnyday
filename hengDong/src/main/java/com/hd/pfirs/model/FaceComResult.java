package com.hd.pfirs.model;

import java.util.Date;

public class FaceComResult {
     private Long faceCompRltID;  //主键ID
     private Date receiveTime;    //接收时间
     private String receiveTimeStamp;   //接收时间的时间戳
     private String groupCode;     //人员组的编号
     private String faceCode;      //人脸采集数据编号
     private String featureID;     //人脸抓拍照片特征值ID
     private String ctrlBaseID;    //缉控库ID
     private Float similarity;     //相似度
     private String compareBaseID;  //对比库的ID
     private byte[] photo;          //照片
     private String orderNum;
     private String remarkDesc;    //备注
     private String deleteStatus;  //删除状态
     private String createName;    //创建人
     private Date createTime;      //创建时间
     private String updateName;    //修改人
     private Date updateTime;     //修改时间
     private String deviceCode;   //设备号
     private String receiveTimeStr;
	public Long getFaceCompRltID() {
		return faceCompRltID;
	}
	public void setFaceCompRltID(Long faceCompRltID) {
		this.faceCompRltID = faceCompRltID;
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getFaceCode() {
		return faceCode;
	}
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}
	public String getFeatureID() {
		return featureID;
	}
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}
	public String getCtrlBaseID() {
		return ctrlBaseID;
	}
	public void setCtrlBaseID(String ctrlBaseID) {
		this.ctrlBaseID = ctrlBaseID;
	}
	public Float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
	}
	public String getCompareBaseID() {
		return compareBaseID;
	}
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}
	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}
	
}

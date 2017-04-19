/**
 * 
 */
package com.hd.pfirs.model;

import java.util.Arrays;

/**
 * 根据客户要求先将图片转码成base64字符串传到前台
 * 除了collectPic的类型不同，其他和Fceinfo相同
 * @author curry.su
 *
 */
public class FaceInfoWebModel {
	/**
	 * auto_increment
	 */
	private long faceId;
	/**
	 * 编号（对应前端人脸采集信息表里的编号）
	 */
	private String faceCode;
	/**
	 * 采集时间
	 */
	private String collectTime;
	/**
	 * 采集时间时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 前端本地保存时间
	 */
	private String frontSaveTime;
	/**
	 * 前端本地保存时间时间戳
	 */
	private String frontSaveTimeStamp;
	/**
	 * 汇聚保存时间
	 */
	private String togthSaveTime;
	/**
	 * 汇聚保存时间时间戳
	 */
	private String togthSaveTimeStamp;
	/**
	 * 发送时间
	 */
	private String relayTime;
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
	 * 辖区（后台数据库里有）
	 */
	private long regionCode;
	/**
	 * 采集地点
	 */
	private String collectSite;
	/**
	 * 人员的组编号
	 */
	private String groupCode;
	/**
	 * 采集照片
	 */
	private String collectPic;
	/**
	 * 图片类型（0-头肩图；1-全身图）
	 */
	private String picType;
	/**
	 * 图片的上报序列号
	 */
	private String picNo;
	/**
	 * 是否为最后一张
	 */
	private String isLast;
	/**
	 * 方向
	 */
	private String direction;
	/**
	 * 速度
	 */
	private String speed;
	/**
	 * 在全图中尺寸
	 */
	private String size;
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
	private String updateDate;

	/**
	 * @return the faceId
	 */
	public long getFaceId() {
		return faceId;
	}

	/**
	 * @param faceId
	 *            the faceId to set
	 */
	public void setFaceId(long faceId) {
		this.faceId = faceId;
	}

	/**
	 * @return the faceCode
	 */
	public String getFaceCode() {
		return faceCode;
	}

	/**
	 * @param faceCode
	 *            the faceCode to set
	 */
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}

	/**
	 * @return the collectTime
	 */
	public String getCollectTime() {
		if(collectTime != "" && collectTime != null){
			return collectTime.substring(11,19);
		}
		return collectTime;
	}

	/**
	 * @param collectTime
	 *            the collectTime to set
	 */
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
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
	public String getFrontSaveTime() {
		return frontSaveTime;
	}

	/**
	 * @param frontSaveTime
	 *            the frontSaveTime to set
	 */
	public void setFrontSaveTime(String frontSaveTime) {
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
	 * @return the togthSaveTime
	 */
	public String getTogthSaveTime() {
		return togthSaveTime;
	}

	/**
	 * @param togthSaveTime
	 *            the togthSaveTime to set
	 */
	public void setTogthSaveTime(String togthSaveTime) {
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
	public String getRelayTime() {
		return relayTime;
	}

	/**
	 * @param relayTime
	 *            the relayTime to set
	 */
	public void setRelayTime(String relayTime) {
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
	public long getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(long regionCode) {
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
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the collectPic
	 */
	public String getCollectPic() {
		return collectPic;
	}

	/**
	 * @param collectPic
	 *            the collectPic to set
	 */
	public void setCollectPic(String collectPic) {
		this.collectPic = collectPic;
	}

	/**
	 * @return the picType
	 */
	public String getPicType() {
		return picType;
	}

	/**
	 * @param picType
	 *            the picType to set
	 */
	public void setPicType(String picType) {
		this.picType = picType;
	}

	/**
	 * @return the picNo
	 */
	public String getPicNo() {
		return picNo;
	}

	/**
	 * @param picNo
	 *            the picNo to set
	 */
	public void setPicNo(String picNo) {
		this.picNo = picNo;
	}

	/**
	 * @return the isLast
	 */
	public String getIsLast() {
		return isLast;
	}

	/**
	 * @param isLast
	 *            the isLast to set
	 */
	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the speed
	 */
	public String getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param fsize
	 *            the fsize to set
	 */
	public void setSize(String size) {
		this.size = size;
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
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "FaceInfoWebModel [faceId=" + faceId + ", faceCode=" + faceCode + ", collectTime=" + collectTime
				+ ", collectTimeStamp=" + collectTimeStamp + ", frontSaveTime=" + frontSaveTime
				+ ", frontSaveTimeStamp=" + frontSaveTimeStamp + ", togthSaveTime=" + togthSaveTime
				+ ", togthSaveTimeStamp=" + togthSaveTimeStamp + ", relayTime=" + relayTime + ", relayTimeStamp="
				+ relayTimeStamp + ", relayFlag=" + relayFlag + ", deviceCode=" + deviceCode + ", regionCode="
				+ regionCode + ", collectSite=" + collectSite + ", groupCode=" + groupCode + ",  picType=" + picType + ", picNo=" + picNo + ", isLast=" + isLast + ", direction="
				+ direction + ", speed=" + speed + ", size=" + size + ", remarkDesc=" + remarkDesc + ", deleteStatus="
				+ deleteStatus + ", createName=" + createName + ", createTime=" + createTime + ", updateName="
				+ updateName + ", updateDate=" + updateDate + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	

}


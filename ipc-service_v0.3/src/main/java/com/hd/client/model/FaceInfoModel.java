package com.hd.client.model;

import java.util.Arrays;

/**
 * 人脸采集信息
 * 
 * @author ligang.yang
 *
 */
public class FaceInfoModel {
	/**
	 * auto_increment
	 */
	private Long faceId;
	/**
	 * 编号（设备id_时间戳_顺番）
	 */
	private String faceCode;
	/**
	 * 采集时间时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 采集地点
	 */
	private String collectSite;
	/**
	 * 人员的组编号（摄像头ID+人员ID唯一标识）？？
	 */
	private String groupCode;
	/**
	 * 采集照片
	 */
	private byte[] collectPic;
	/**
	 * 图片类型（0-头肩图；1-全身图）
	 */
	private String picType;
	/**
	 * 图片的上报序列号
	 */
	private String picNo;
	/**
	 * 是否为最后一张（0-不是；1-是）
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
	 * 转发时间戳
	 */
	private String relayTime;
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
	private String updateDate;

	public Long getFaceId() {
		return faceId;
	}

	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}

	public String getFaceCode() {
		return faceCode;
	}

	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}

	public String getCollectTimeStamp() {
		return collectTimeStamp;
	}

	public void setCollectTimeStamp(String collectTimeStamp) {
		this.collectTimeStamp = collectTimeStamp;
	}

	public String getCollectSite() {
		return collectSite;
	}

	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public byte[] getCollectPic() {
		return collectPic;
	}

	public void setCollectPic(byte[] collectPic) {
		this.collectPic = collectPic;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getPicNo() {
		return picNo;
	}

	public void setPicNo(String picNo) {
		this.picNo = picNo;
	}

	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRelayTime() {
		return relayTime;
	}

	public void setRelayTime(String relayTime) {
		this.relayTime = relayTime;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "FaceInfoModel [faceId=" + faceId + ", faceCode=" + faceCode + ", collectTimeStamp=" + collectTimeStamp
				+ ", collectSite=" + collectSite + ", groupCode=" + groupCode + ", collectPic="
				+ Arrays.toString(collectPic) + ", picType=" + picType + ", picNo=" + picNo + ", isLast=" + isLast
				+ ", direction=" + direction + ", speed=" + speed + ", size=" + size + ", relayTime=" + relayTime
				+ ", relayFlag=" + relayFlag + ", remarkDesc=" + remarkDesc + ", deleteStatus=" + deleteStatus
				+ ", createName=" + createName + ", createTime=" + createTime + ", updateName=" + updateName
				+ ", updateDate=" + updateDate + "]";
	}

}

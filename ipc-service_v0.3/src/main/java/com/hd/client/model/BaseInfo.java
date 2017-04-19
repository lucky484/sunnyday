package com.hd.client.model;

/**
 * 采集设备基础信息
 * @author ligang.yang
 *
 */
public class BaseInfo {
	/**
	 * 人脸设备的镜头型号
	 */
	private String faceDvLensModel;
	
	/**
	 * 人脸设备的采集照片间隔（帧数）
	 */
	private String faceDvCollInterval;
	
	/**
	 * 人脸设备的IP
	 */
	private String faceDvIP;

	/**
	 * 人脸设备的视频URL
	 */
	private String faceDvURL;
	
	/**
	 * 身份证设备的序列号（唯一的）
	 */
	private String iDCardDvNo;
	
	/**
	 * 视频设备（球机）的唯一标识ID
	 */
	private String videoID;

	/**
	 * 视频设备（球机）的URL
	 */
	private String videoIP;


	/**
	 * 采集数据转发的IP
	 */
	private String replyIP;
	
	/**
	 * 采集数据本地存储期限(天)
	 */
	private int storePerio;
	
	/**
	 * 转发间隔时间（轮询策略）（秒）
	 */
	private int intervalTime;
	
	/**
	 * 人脸识别一组照片的采集数量（2张、3张、5张）
	 */
	private String groupPicNum;

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
	 *  更新人
	 */
	private String updateName;
	
	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * @return the faceDvLensModel
	 */
	public String getFaceDvLensModel() {
		return faceDvLensModel;
	}

	/**
	 * @param faceDvLensModel the faceDvLensModel to set
	 */
	public void setFaceDvLensModel(String faceDvLensModel) {
		this.faceDvLensModel = faceDvLensModel;
	}

	/**
	 * @return the faceDvCollInterval
	 */
	public String getFaceDvCollInterval() {
		return faceDvCollInterval;
	}

	/**
	 * @param faceDvCollInterval the faceDvCollInterval to set
	 */
	public void setFaceDvCollInterval(String faceDvCollInterval) {
		this.faceDvCollInterval = faceDvCollInterval;
	}

	/**
	 * @return the faceDvIP
	 */
	public String getFaceDvIP() {
		return faceDvIP;
	}

	/**
	 * @param faceDvIP the faceDvIP to set
	 */
	public void setFaceDvIP(String faceDvIP) {
		this.faceDvIP = faceDvIP;
	}

	/**
	 * @return the faceDvURL
	 */
	public String getFaceDvURL() {
		return faceDvURL;
	}

	/**
	 * @param faceDvURL the faceDvURL to set
	 */
	public void setFaceDvURL(String faceDvURL) {
		this.faceDvURL = faceDvURL;
	}

	/**
	 * @return the iDCardDvNo
	 */
	public String getiDCardDvNo() {
		return iDCardDvNo;
	}

	/**
	 * @param iDCardDvNo the iDCardDvNo to set
	 */
	public void setiDCardDvNo(String iDCardDvNo) {
		this.iDCardDvNo = iDCardDvNo;
	}

	/**
	 * @return the videoID
	 */
	public String getVideoID() {
		return videoID;
	}

	/**
	 * @param videoID the videoID to set
	 */
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}

	/**
	 * @return the videoIP
	 */
	public String getVideoIP() {
		return videoIP;
	}

	/**
	 * @param videoIP the videoIP to set
	 */
	public void setVideoIP(String videoIP) {
		this.videoIP = videoIP;
	}

	/**
	 * @return the replyIP
	 */
	public String getReplyIP() {
		return replyIP;
	}

	/**
	 * @param replyIP the replyIP to set
	 */
	public void setReplyIP(String replyIP) {
		this.replyIP = replyIP;
	}

	/**
	 * @return the storePerio
	 */
	public int getStorePerio() {
		return storePerio;
	}

	/**
	 * @param storePerio the storePerio to set
	 */
	public void setStorePerio(int storePerio) {
		this.storePerio = storePerio;
	}

	/**
	 * @return the intervalTime
	 */
	public int getIntervalTime() {
		return intervalTime;
	}

	/**
	 * @param intervalTime the intervalTime to set
	 */
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	/**
	 * @return the groupPicNum
	 */
	public String getGroupPicNum() {
		return groupPicNum;
	}

	/**
	 * @param groupPicNum the groupPicNum to set
	 */
	public void setGroupPicNum(String groupPicNum) {
		this.groupPicNum = groupPicNum;
	}

	/**
	 * @return the remarkDesc
	 */
	public String getRemarkDesc() {
		return remarkDesc;
	}

	/**
	 * @param remarkDesc the remarkDesc to set
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
	 * @param deleteStatus the deleteStatus to set
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
	 * @param createName the createName to set
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
	 * @param createTime the createTime to set
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
	 * @param updateName the updateName to set
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
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	

}

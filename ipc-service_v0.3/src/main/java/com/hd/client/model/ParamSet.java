package com.hd.client.model;

public class ParamSet {
	/**
	 * auto_increment
	 */
	private long paramId;
	/**
	 * 设备编号
	 */
	private String deviceCode;
	/**
	 * 设备类型（移动；固定）
	 */
	private String deviceType;
	/**
	 * 采集地点（中文）
	 */
	private String collectSite;
	/**
	 * 人脸设备的序列号（唯一的）
	 */
	private String faceDvNo;
	/**
	 * 人脸设备的设备型号
	 */
	private String faceDvModel;
	/**
	 * 人脸设备的镜头型号
	 */
	private String faceDvLensModel;
	/**
	 * 人脸设备的采集照片间隔（帧数）
	 */
	private int faceDvCollInterval;
	/**
	 * 人脸设备的IP
	 */
	private String faceDvIp;
	/**
	 * 人脸设备的视频URL
	 */
	private String faceDvUrl;
	/**
	 * 身份证设备的序列号（唯一的）
	 */
	private String idCardDvNo;
	/**
	 * 视频设备（球机）的唯一标识ID
	 */
	private String videoId;
	/**
	 * 视频设备（球机）的URL
	 */
	private String videoIp;
	/**
	 * 采集数据转发的IP
	 */
	private String replyIp;
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
	private int groupPicNum;
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
	 * @return the paramId
	 */
	public long getParamId() {
		return paramId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCollectSite() {
		return collectSite;
	}

	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}

	public String getFaceDvNo() {
		return faceDvNo;
	}

	public void setFaceDvNo(String faceDvNo) {
		this.faceDvNo = faceDvNo;
	}

	public String getFaceDvModel() {
		return faceDvModel;
	}

	public void setFaceDvModel(String faceDvModel) {
		this.faceDvModel = faceDvModel;
	}

	public String getFaceDvLensModel() {
		return faceDvLensModel;
	}

	public void setFaceDvLensModel(String faceDvLensModel) {
		this.faceDvLensModel = faceDvLensModel;
	}

	public int getFaceDvCollInterval() {
		return faceDvCollInterval;
	}

	public void setFaceDvCollInterval(int faceDvCollInterval) {
		this.faceDvCollInterval = faceDvCollInterval;
	}

	public String getFaceDvIp() {
		return faceDvIp;
	}

	public void setFaceDvIp(String faceDvIp) {
		this.faceDvIp = faceDvIp;
	}

	public String getFaceDvUrl() {
		return faceDvUrl;
	}

	public void setFaceDvUrl(String faceDvUrl) {
		this.faceDvUrl = faceDvUrl;
	}

	public String getIdCardDvNo() {
		return idCardDvNo;
	}

	public void setIdCardDvNo(String idCardDvNo) {
		this.idCardDvNo = idCardDvNo;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoIp() {
		return videoIp;
	}

	public void setVideoIp(String videoIp) {
		this.videoIp = videoIp;
	}

	public String getReplyIp() {
		return replyIp;
	}

	public void setReplyIp(String replyIp) {
		this.replyIp = replyIp;
	}

	public int getStorePerio() {
		return storePerio;
	}

	public void setStorePerio(int storePerio) {
		this.storePerio = storePerio;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public int getGroupPicNum() {
		return groupPicNum;
	}

	public void setGroupPicNum(int groupPicNum) {
		this.groupPicNum = groupPicNum;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}

}

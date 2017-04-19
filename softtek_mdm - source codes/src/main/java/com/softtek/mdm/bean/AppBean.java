package com.softtek.mdm.bean;

import java.io.Serializable;

/**
 * 应用商店-应用Bean
 * @author jane.hui
 */
public class AppBean implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 应用主键
	 */
	private Integer id;
	
	/**
	 * 应用名称
	 */
	private String appName;
	
	/**
	 * icon路径(全路径)
	 */
	private String iconPath;
	
	/**
	 * 应用Id
	 */
	private String appId;
	
	/**
	 * 应用安装人数
	 */
	private Integer appCount;
	
	/**
	 * apk下载地址
	 */
	private String downloadUrl;
	
	/**
	 * apk路径
	 */
	private String filePath;

	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 文件大小
	 */
	private String fileSize;
	
	/**
	 * 上下架状态(1.上架中,0.下架中)
	 */
	private String state;
	
	/**
	 * 删除时间(不为空代表已删除，为空代表未删除)
	 */
	private String deleteTime;
	
	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * itune store id
	 */
	private String trackId;

	/**
	 * 应用版本号
	 */
	private String appVersion;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getAppCount() {
		return appCount;
	}

	public void setAppCount(Integer appCount) {
		this.appCount = appCount;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
}

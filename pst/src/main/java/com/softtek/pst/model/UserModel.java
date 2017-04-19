package com.softtek.pst.model;

import java.util.Date;

/**
 * 
 * @ClassName: UserModel
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 8, 2016 9:16:08 AM
 */
public class UserModel {
	/**
	 * 主键UserId
	 */
	private long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String userPassword;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 英文名
	 */
	private String englishName;
	/**
	 * 中文名
	 */
	private String chineseName;

	private long roleId;
	private Role role;
	
	private String showInfo;
	
	private String authorityUrl;
	
	private String authorityName;
	
	public String email;
	
	public int isNotification;

	public UserModel() {
	}

	public UserModel(long userId, String userName, String userPassword, Date lastLoginTime, Date createTime, Date updateTime, long roleId, Role role, String englishName, String chineseName,String showInfo,String email,int isNotification) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.lastLoginTime = lastLoginTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.roleId = roleId;
		this.role = role;
		this.englishName = englishName;
		this.chineseName = chineseName;
		this.showInfo = showInfo;
		this.email = email;
		this.isNotification = isNotification;
	}

	/**
	 * @return the UserId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param UserId
	 *            the UserId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}

	public String getAuthorityUrl() {
		return authorityUrl;
	}

	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsNotification() {
		return isNotification;
	}

	public void setIsNotification(int isNotification) {
		this.isNotification = isNotification;
	}


}

package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

import com.f2b2c.eco.model.platform.FUserModel;

public class BShopInfoToCModel implements Serializable {
	/**
	 * 商铺ID
	 */
	private Integer id;

	/**
	 * 商铺老板ID，商铺用户ID
	 */
	private BUserModel user;

	/**
	 * 商铺名称
	 */
	private String shopName;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 身份证号
	 */
	private String identityId;

	/**
	 * 坐标X
	 */
	private String locationX;

	/**
	 * 坐标Y
	 */
	private String locationY;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 创建人
	 */
	private FUserModel createdUser;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 授权码
	 */
	private String authCode;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 详情
	 */
	private String details;
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 距离
	 */
	private double length;

	/**
	 * 是否收藏
	 */
	private Integer isFavorite;

	public Integer getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BUserModel getUser() {
		return user;
	}

	public void setUser(BUserModel user) {
		this.user = user;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public FUserModel getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(FUserModel createdUser) {
		this.createdUser = createdUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BShopInfoToCModel [id=" + id + ", user=" + user + ", shopName=" + shopName + ", userName=" + userName
				+ ", identityId=" + identityId + ", locationX=" + locationX + ", locationY=" + locationY + ", address="
				+ address + ", createdTime=" + createdTime + ", createdUser=" + createdUser + ", updatedTime="
				+ updatedTime + ", authCode=" + authCode + ", deletedTime=" + deletedTime + ", details=" + details
				+ ", phone=" + phone + ", length=" + length + ", isFavorite=" + isFavorite + "]";
	}

	public String getLocationX() {
		return locationX;
	}

	public String getLocationY() {
		return locationY;
	}
}
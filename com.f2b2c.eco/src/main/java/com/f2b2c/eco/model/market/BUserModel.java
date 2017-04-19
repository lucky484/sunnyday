package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.f2b2c.eco.model.platform.FUserModel;

public class BUserModel implements Serializable {
	/**
	 * 商户用户ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "账户名不能为空！")
	private String accountName;

	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空！")
	@Length(min = 6, max = 20, message = "密码在6到20个字符之间！")
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别0男1女
	 */
	private Integer sex;

	/**
	 * 身份证号
	 */
	private String identityId;

	/**
	 * 用户是否激活0激活，1锁定
	 */
	private Integer isActive;

	/**
	 * 创建人
	 */
	private FUserModel createdUser;

	/**
	 * 修改人
	 */
	private String createdTime;

	/**
	 * 更新用户
	 */
	private FUserModel updatedUser;

	/**
	 * 更新时间
	 */
	private String updatedTime;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 短信验证码
	 */
	private String validCode;

	/**
	 * 授权码
	 */
	private String authCode;

	/**
	 * 商店名称
	 */
	private String shopName;

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 所属区域id
	 */
	private Integer areaId;
	/**
	 * 坐标X
	 */
	private String locationX;

	/**
	 * 坐标Y
	 */
	private String locationY;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public FUserModel getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(FUserModel createdUser) {
		this.createdUser = createdUser;
	}

	public FUserModel getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(FUserModel updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
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
	
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BUserModel [id=");
		builder.append(id);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", identityId=");
		builder.append(identityId);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdUser=");
		builder.append(createdUser);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", updatedUser=");
		builder.append(updatedUser);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", deletedTime=");
		builder.append(deletedTime);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", address=");
		builder.append(address);
		builder.append(", validCode=");
		builder.append(validCode);
		builder.append(", authCode=");
		builder.append(authCode);
		builder.append(", shopName=");
		builder.append(shopName);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", areaId=");
		builder.append(areaId);
		builder.append(", locationX=");
		builder.append(locationX);
		builder.append(", locationY=");
		builder.append(locationY);
		builder.append("]");
		return builder.toString();
	}

}
package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 插入收货地址表
 * @author jane.hui
 *
 */
public class InsertCDeliveryAddressModel implements Serializable {
	
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户外键
     */
    private Integer userId;
    
    /**
     * 收货人
     * @mbggenerated
     */
    private String consignee;

    /**
     * 区域外键
     * @mbggenerated
     */
    private Integer areaId;

    /**
     * 详细地址
     * @mbggenerated
     */
    private String address;

    /**
     * 手机号
     * @mbggenerated
     */
    private String mobile;

    /**
     * 电话
     * @mbggenerated
     */
    private String telephone;

    /**
     * 邮箱
     * @mbggenerated
     */
    private String email;

    /**
     * 是否默认
     * @mbggenerated
     */
    private String isDefault;
    
    /**
     * 创建者
     * @mbggenerated
     */
    private Integer createdUser;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * 更新者
     * @mbggenerated
     */
    private Integer updatedUser;

    /**
     * 更新时间
     * @mbggenerated
     */
    private Date updatedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(Integer updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
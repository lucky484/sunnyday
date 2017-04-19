package com.f2b2c.eco.model.platform;

import com.f2b2c.eco.model.common.BaseModel;

/**
 * 平台发货地址（商铺收货地址）对象，
 * 
 * @author brave.chen
 *
 */
public class FDeliveryAddress extends BaseModel {

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 收货人姓名
	 */
	private String consignee;

	/**
	 * 收货地址区ID
	 */
	private Integer areaId;

	/**
	 * 收货地址城市ID
	 */
	private Integer cityId;

	/**
	 * 收货地址省份ID
	 */
	private Integer provinceId;

	/**
	 * 详细收货地址
	 */
	private Integer address;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 是否是默认地址
	 */
	private Integer isDefault;

	/**
	 * 收货人ID
	 */
	private Integer userId;

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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
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

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FDeliveryAddress [id=");
		builder.append(id);
		builder.append(", consignee=");
		builder.append(consignee);
		builder.append(", areaId=");
		builder.append(areaId);
		builder.append(", cityId=");
		builder.append(cityId);
		builder.append(", provinceId=");
		builder.append(provinceId);
		builder.append(", address=");
		builder.append(address);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", isDefault=");
		builder.append(isDefault);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}

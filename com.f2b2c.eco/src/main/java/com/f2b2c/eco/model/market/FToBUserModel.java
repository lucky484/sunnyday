package com.f2b2c.eco.model.market;

public class FToBUserModel {

	/**
	 * 商店名称
	 */
	private String shopName;

	/**
	 * 店铺头像
	 */
	private String shopLogoUrl;

	/**
	 * 今日订单数
	 */
	private Integer currentDateSaleCount;

	/**
	 * 今日销售额
	 */
	private Integer currentDatePriceCount;

	/**
	 * 店长信息
	 */
	private String realName;

	/**
	 * 店铺电话
	 */
	private String phone;

	/**
	 * 店铺地址
	 */
	private Integer addressId;

	private String address;

	private String areaName;

	private String cityName;

	private String provinceName;
	
	private String provinceCode;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLogoUrl() {
		return shopLogoUrl;
	}

	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}

	public Integer getCurrentDateSaleCount() {
		return currentDateSaleCount;
	}

	public void setCurrentDateSaleCount(Integer currentDateSaleCount) {
		this.currentDateSaleCount = currentDateSaleCount;
	}

	public Integer getCurrentDatePriceCount() {
		return currentDatePriceCount;
	}

	public void setCurrentDatePriceCount(Integer currentDatePriceCount) {
		this.currentDatePriceCount = currentDatePriceCount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

}

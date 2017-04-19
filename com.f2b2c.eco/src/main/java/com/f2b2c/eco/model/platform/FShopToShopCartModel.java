package com.f2b2c.eco.model.platform;

/**
 * 商户信息
 * @author jane.hui
 *
 */
public class FShopToShopCartModel {
	
	/**
	 * 店铺id
	 */
	private Integer id;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 店铺地址
	 */
	private String address;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 经纬度x
	 */
	private String locationX;
	
	/**
	 * 经纬度y
	 */
	private String locationY;
	
	/**
	 * 区域id
	 */
	private Integer areaId;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 城市id
	 */
	private Integer cityId;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 省id
	 */
	private Integer provinceId;
	
	/**
	 * 省名称
	 */
	private String provinceName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}

package com.f2b2c.eco.model.common;

import java.util.List;

/**
 * 省份对象model
 * 
 * @author brave.chen
 *
 */
public class ProvinceModel{
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 省份code
	 */
	private String code;

	/**
	 * 省份名称
	 */
	private String name;

	/**
	 * 省份包含的城市列表
	 */
	private List<CityModel> cityModelList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityModelList() {
		return cityModelList;
	}

	public void setCityModelList(List<CityModel> cityModelList) {
		this.cityModelList = cityModelList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProvinceModel [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", cityModelList=");
		builder.append(cityModelList);
		builder.append("]");
		return builder.toString();
	}
}

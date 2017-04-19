package com.f2b2c.eco.model.common;

import java.util.List;

/**
 * 城市对象model
 * 
 * @author brave.chen
 *
 */
public class CityModel{
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 城市编码
	 */
	private String code;

	/**
	 * 城市名称
	 */
	private String name;

	/**
	 * 城市所属省份code
	 */
	private String provinceCode;

	/**
	 * 区对象模型列表
	 */
	private List<AreaModel> areaModelList;

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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public List<AreaModel> getAreaModelList() {
		return areaModelList;
	}

	public void setAreaModelList(List<AreaModel> areaModelList) {
		this.areaModelList = areaModelList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CityModel [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", provinceCode=");
		builder.append(provinceCode);
		builder.append(", areaModelList=");
		builder.append(areaModelList);
		builder.append("]");
		return builder.toString();
	}
}

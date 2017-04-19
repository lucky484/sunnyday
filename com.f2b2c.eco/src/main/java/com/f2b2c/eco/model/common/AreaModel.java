package com.f2b2c.eco.model.common;

/**
 * 区对象模型
 * 
 * @author brave.chen
 *
 */
public class AreaModel {
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 地域码
	 */
	private String code;

	/**
	 * 地域名称
	 */
	private String name;

	/**
	 * 城市编码
	 */
	private String cityCode;

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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AreaModel [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", cityCode=");
		builder.append(cityCode);
		builder.append("]");
		return builder.toString();
	}
}

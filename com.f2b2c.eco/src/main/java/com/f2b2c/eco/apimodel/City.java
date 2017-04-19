package com.f2b2c.eco.apimodel;

/**
 * 接口城市对象
 * 
 * @author brave.chen
 *
 */
public class City {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", provinceCode=");
		builder.append(provinceCode);
		builder.append("]");
		return builder.toString();
	}
}

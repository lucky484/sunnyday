package com.f2b2c.eco.apimodel;

/**
 * 接口层省份对象
 * 
 * @author brave.chen
 *
 */
public class Province {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Province [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}

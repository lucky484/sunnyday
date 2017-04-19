package com.f2b2c.eco.model.common;

/**
 * 字典表
 * @author jzhu
 *
 */
public class Dictionary {

	private Integer id;
	private String name;
	private String value;
	private String defvalue;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDefvalue() {
		return defvalue;
	}
	public void setDefvalue(String defvalue) {
		this.defvalue = defvalue;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dictionary [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append(", defvalue=");
		builder.append(defvalue);
		builder.append("]");
		return builder.toString();
	}
	
}

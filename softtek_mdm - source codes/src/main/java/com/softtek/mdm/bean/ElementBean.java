package com.softtek.mdm.bean;

import java.util.List;

/**
 * 元素bean
 * @author jane.hui
 */
public class ElementBean {

	/**
	 * plist标识的key
	 */
	private String key;
	
	/**
	 * plist标识的value
	 */
	private String value;
	
	/**
	 * plist组织结构(key,string方式)
	 */
	private String tag;
	
	/**
	 * plist组织结构(key,array方式)
	 */
	private List<String> arrayList;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getArrayList() {
		return arrayList;
	}

	public void setArrayList(List<String> arrayList) {
		this.arrayList = arrayList;
	}
}

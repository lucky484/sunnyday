package com.f2b2c.eco.dao.platform;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台分页
 * @author jane.hui
 *
 */
public class Page {

	// 当前页
	private int start = 1;

	// 页容量
	private int length;
	
	private Map<String,Object> params = new HashMap<String,Object>(0);

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}

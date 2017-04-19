package com.f2b2c.eco.model.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据对象
 */
public class DataGridModel implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7232798260610351343L;

	/**
	 * 参数map
	 */
	private Map<String, Object> params = new HashMap<String, Object>(0);

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}

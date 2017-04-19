package com.f2b.sugar.web.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: 居泽平  Date: 14-3-3, 上午9:29
 */
public class ExcelImportConfig {

	private Map<Integer, String> cellPropertyMap = new HashMap<Integer, String>();

	private Map<String, String> extraParameterMap = new HashMap<String, String>();

	private Map<String, String> dateConvertTypeMap = new HashMap<String, String>();

	public Map<Integer, String> getCellPropertyMap() {
		return cellPropertyMap;
	}

	public void setCellPropertyMap(Map<Integer, String> cellPropertyMap) {
		this.cellPropertyMap = cellPropertyMap;
	}

	public Map<String, String> getExtraParameterMap() {
		return extraParameterMap;
	}

	public void setExtraParameterMap(Map<String, String> extraParameterMap) {
		this.extraParameterMap = extraParameterMap;
	}

	public Map<String, String> getDateConvertTypeMap() {
		return dateConvertTypeMap;
	}

	public void setDateConvertTypeMap(Map<String, String> dateConvertTypeMap) {
		this.dateConvertTypeMap = dateConvertTypeMap;
	}
}

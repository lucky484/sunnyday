package com.f2b2c.eco.model.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {

	// 当前页
	private int page = 1;

	// 页容量
	private int pageSize;

	// 总条数
	private int totalCount;

	private int start;

	private Object JSONArray;

	private List<? extends Object> rows;

	private Map<String,Object> params = new HashMap<String,Object>(0);
	
	/**
	 * 通过构造方法传入当前页和总条数
	 * 
	 * @param totalCount
	 * @param page
	 * @param pageSize
	 */
	public Page(int totalCount, int page, int pageSize) {
		this.totalCount = totalCount;
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public Page(){}

	public Object getJSONArray() {
		return JSONArray;
	}

	public void setJSONArray(Object jSONArray) {
		JSONArray = jSONArray;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		start = (getPage() - 1) * pageSize;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public List<? extends Object> getRows() {
		return rows;
	}

	public void setRows(List<? extends Object> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}

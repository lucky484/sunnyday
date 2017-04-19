package com.softtek.mdm.model;

import java.util.List;

/**
 * 
 * @author jing.liu
 *
 */

public class Page {
	/**
	 * 数据内容
	 */
	@SuppressWarnings("rawtypes")
	protected List data;
	/**
	 * 
	 */
	protected int draw;
	/**
	 * 总记录数
	 */
	protected long recordsTotal;
	/**
	 * 
	 */
	protected long recordsFiltered;
	@SuppressWarnings("rawtypes")
	public List getData() {
		return data;
	}
	@SuppressWarnings("rawtypes")
	public void setData(List data) {
		this.data = data;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	} 
	
	public Page(){}
	
	public Page(List data,long recordsTotal,long recordsFiltered) {
		this.data=data;
		this.recordsFiltered=recordsFiltered;
		this.recordsTotal=recordsTotal;
	}
 

}

package com.softtek.pst.util;

import java.util.List;

/**
 * @ClassName: Page
 * @Description: TODO
 * @author kaishen
 * @date Nov 1, 2015 10:35:04 PM
 * 
 */

public class Page<T> {
	protected List<T> data;
	protected int draw;
	protected long recordsTotal;
	protected long recordsFiltered;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
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
 

}

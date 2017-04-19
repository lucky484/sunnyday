package com.f2b.sugar.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 居泽平  Date: 14/12/10, 09:53
 */
@SuppressWarnings("unused")
public class QueryData<T> {

	private Long totalNum;
	private List<T> dataList;

	public Long getTotalNum() {
		return totalNum;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public QueryData(List<T> dataList, Long totalNum) {

		if (dataList == null) {
			dataList = new ArrayList<>();
		}
		if (totalNum == null) {
			totalNum = 0L;
		}

		this.totalNum = totalNum;
		this.dataList = dataList;
	}
}

package com.f2b.sugar.reference;

/**
 * Author: 居泽平  Date: 14-6-19, 9:29
 */
public class ForOrder implements Comparable {

	private Object object;

	private Integer keyNum;

	public ForOrder() {
	}

	public ForOrder(Object object, Integer keyNum) {
		this.object = object;
		this.keyNum = keyNum;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Integer getKeyNum() {
		return keyNum;
	}

	public void setKeyNum(Integer keyNum) {
		this.keyNum = keyNum;
	}

	@Override
	public int compareTo(Object o) {
		Integer result = this.getKeyNum() - ((ForOrder) o).getKeyNum();
		if (result > 0) {
			return -1;
		} else if (result < 0) {
			return 1;
		} else {
			return 0;
		}
	}
}

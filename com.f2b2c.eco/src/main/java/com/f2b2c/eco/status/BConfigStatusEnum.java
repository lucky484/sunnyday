package com.f2b2c.eco.status;

public enum BConfigStatusEnum {

	ENABLED("1"),
	UNABLED("0");
	
	private String displayName;
	
	private BConfigStatusEnum(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
	
}

package com.softtek.mdm.status;

public enum DeviceTypeStatus {
	
	SOFTTEK_ANDROID("android"),
	SOFTTEK_IOS("ios");
	
	private String displayName;
		
	private DeviceTypeStatus(final String name){
		this.displayName = name;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}

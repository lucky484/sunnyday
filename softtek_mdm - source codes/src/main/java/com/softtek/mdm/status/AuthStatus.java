package com.softtek.mdm.status;


public enum AuthStatus {
	
	SOFTTEK_AMDIN("1"),
	SOFTTEK_INSTITUTION("2"),
	SOFTTEK_MANAGER("2"),
	SOFTTEK_CUSTOMER("4"),
	SOFTTEK_DPT_MANAGER("3"),
	AUTH_INSTITUTION("AUTH_INSTITUTION"),
	AUTH_CUSTOMER("AUTH_CUSTOMER"),
	AUTH_ADMIN("AUTH_ADMIN");
	
	private String displayName;
	
	private AuthStatus(final String name){
		this.displayName = name;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}

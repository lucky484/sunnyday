package com.f2b2c.eco.status;

public enum RoleTypeEnum {

	ADMIN("1"),//超级管理员
	PROVINCE("2"),//省级合伙人
	CITY("3"),//市级合伙人
	DISTRICT("4"),//区域合伙人
	SERVER("5"),//专属顾问
	PURCHASE("6"),//采购
	CAIWU("7"),//财务
	WULIU("8"),//物流
	YUNYING("9");//运营
	
	private String displayName;
	
	private RoleTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
	
	
	
}

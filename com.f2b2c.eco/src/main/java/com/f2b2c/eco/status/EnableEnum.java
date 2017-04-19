package com.f2b2c.eco.status;

/**
 * 分类
 * @author jane.hui
 *
 */
public enum EnableEnum {
    
    /** 啟用 */
    enable("1"),

    /** 禁用 */
    disable("0");
	
	private String displayName;
	
	private EnableEnum(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}

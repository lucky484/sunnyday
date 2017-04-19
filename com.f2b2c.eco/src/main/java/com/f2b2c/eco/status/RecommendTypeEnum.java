package com.f2b2c.eco.status;

/**
 * 推荐类型
 * @author jane.hui
 *
 */
public enum RecommendTypeEnum {

    /** 0表示消费者推荐 */
    user("0"),

    /** 1表示商户推荐 */
    merchant("1");
	
	private String displayName;
	
	private RecommendTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}

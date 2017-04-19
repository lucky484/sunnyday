package com.f2b2c.eco.status;
/**
 * B端商品状态枚举类
 * @author jzhu
 *
 */
public enum BGoodsStatusEnum {

	WAIT_AUDIT("0"),//待审核
	ON_SHELF("1"),//上架
	OFF_SHELF("2"),//下架
	NO_PASS("3"),//审核不通过
	ON_DRAFT("4");//草稿
	
	private String displayName;
	
	private BGoodsStatusEnum(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
	
}

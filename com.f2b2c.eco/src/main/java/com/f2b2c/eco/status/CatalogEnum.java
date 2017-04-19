package com.f2b2c.eco.status;

/**
 * 分类
 * @author jane.hui
 *
 */
public enum CatalogEnum {
    
    /** 水果分类 */
    fruit("0"),

    /** 非水果分类 */
    nonfruit("1");
	
	private String fruitName;
	
	private CatalogEnum(String fruitName) {
		this.fruitName = fruitName;
	}

	@Override
	public String toString() {
		return this.fruitName;
	}
}

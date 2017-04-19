package com.f2b2c.eco.model.platform;

/**
 * 购物车Model
 * @author jane.hui
 *
 */
public class FGoodsToShopCarModel {

	/**
	 * 购物车id
	 */
	private Integer cartId;
	
	/**
	 * 购买数量
	 */
	private Integer goodsQty;
	
	/**
	 * 商品id
	 */
	private Integer goodsId;
	
	/**
	 * 商品编号
	 */
	private String goodsNo;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品详情
	 */
	private String detail;
	
	/**
	 * 商品图片
	 */
	private String logoUrl;
	
	/**
	 * 单位
	 */
	private Integer unit;
	
	/**
	 * 单价
	 */
	private Integer price;
	
	/**
	 * 销量
	 */
	private Integer sellQty;
	
	/**
	 * 库存
	 */
	private Integer remain;
	
	/**
	 * 锁定数量
	 */
	private Integer allocate;
	
	/**
	 * 商品简介
	 */
	private String intro;
	
	/**
	 * 商品类型
	 */
	private Integer type;
	
	/**
	 * 商品属性
	 */
	private String properties;
	
	/**
	 * 
	 */
	private Integer version;

	/**
	 * 商品规格
	 */
	private String spec;

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSellQty() {
		return sellQty;
	}

	public void setSellQty(Integer sellQty) {
		this.sellQty = sellQty;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getAllocate() {
		return allocate;
	}

	public void setAllocate(Integer allocate) {
		this.allocate = allocate;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
}

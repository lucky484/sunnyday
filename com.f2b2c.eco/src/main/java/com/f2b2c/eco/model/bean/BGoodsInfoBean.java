package com.f2b2c.eco.model.bean;

/**
 * 商品信息
 * @author jane.hui
 *
 */
public class BGoodsInfoBean {
	
	/**
	 * 购物车id
	 */
	private Integer cartId;
	
	/**
	 * 商品主键
	 */
	private Integer goodsId;
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * logo图片
	 */
	private String logoUrl;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 销量
	 */
	private Integer sales;
	
	/**
	 * 库存
	 */
	private Integer remain;
	
	/**
	 * 锁定商品数量
	 */
	private Integer allocate;
	
	/**
	 * 商品购买数量
	 */
	private Integer goodsQuantity;
	
	/**
	 * 版本
	 */
	private Integer goodsVersion;
	
	/**
	 * 单位
	 */
	private String goodsUnit;
	
	/**
	 * 价格
	 */
	private Integer goodsPrice;
	
	/**
	 * 是否是水果分类(0.水果分类 1.非水果分类)
	 */
	private String catalog;
	
	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
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

	public Integer getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(Integer goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public Integer getGoodsVersion() {
		return goodsVersion;
	}

	public void setGoodsVersion(Integer goodsVersion) {
		this.goodsVersion = goodsVersion;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}	
}

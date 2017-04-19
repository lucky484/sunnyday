package com.f2b2c.eco.model.market;

public class BGoodsInfoToCModel {

	/**
	 * 商品id
	 */
	private Integer id;

	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商店名称
	 */
	private String shopName;

	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 商品图片
	 */
	private String logoUrl;

	/**
	 * 商品价格
	 */
	private Integer price;

	/**
	 * 商品销量
	 */
	private Integer sales;

	/**
	 * 商品单位
	 */
	private String unit;

	/**
	 * 商铺ID
	 */
	private Integer shopId;
	
	/**
	 * 商户外键
	 */
	private Integer bUserId;
	
	/**
	 * 库存
	 */
	private Integer remain;
	
	/**
	 * 购买数量
	 */
	private Integer quantity;

	private Integer kindId;

	private String kindName;

	private String produce_place;

	private String goodsNo;

	private String intro;
	
	private Integer isFavorite;
	/**
	 * 版本号
	 */
	private Integer version;

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getIntro() {
		return intro;
	}

	public Integer getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getKindId() {
		return kindId;
	}

	public String getProduce_place() {
		return produce_place;
	}

	public void setProduce_place(String produce_place) {
		this.produce_place = produce_place;
	}

	public void setKindId(Integer kindId) {
		this.kindId = kindId;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getbUserId() {
		return bUserId;
	}

	public void setbUserId(Integer bUserId) {
		this.bUserId = bUserId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

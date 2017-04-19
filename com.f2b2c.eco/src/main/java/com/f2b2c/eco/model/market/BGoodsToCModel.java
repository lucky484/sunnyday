package com.f2b2c.eco.model.market;

public class BGoodsToCModel {

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
	 * 商店名称
	 */
	private String goodsNo;

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
	private double price;

	/**
	 * 商铺坐标维度
	 */
	private String locationX;

	/**
	 * 商品坐标经度
	 */
	private String locationY;

	/**
	 * 亮点之间的距离
	 */
	private double length;

	/**
	 * 商店id
	 */
	private Integer shopId;

	/**
	 * 商品数量
	 */
	private Integer quantity;

	private String spec;

	private Integer sales;

	/**
	 * 商品状态 ：1正常 0 失效
	 */
	private Integer status;

	/**
	 * 退换货状态
	 */
	private Integer goodsStatus;

	private String orderDetailsId;
	
	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public String getSpec() {
		return spec;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getId() {
		return id;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLocationX() {
		return locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getOrderDetailsId() {
		return orderDetailsId;
	}

	public void setOrderDetailsId(String orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}

}

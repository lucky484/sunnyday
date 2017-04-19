package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FSalesOrderDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 平台订单号
	 */
	private FSalesOrderModel order;

	/**
	 * 商品ID
	 */
	private FGoodsModel goods;

	/**
	 * 平台商品版本
	 */
	private Integer goodsVersion;

	/**
	 * 商品数量
	 */
	private Integer goodsQty;

	/**
	 * 商品价格(分)
	 */
	private Integer price;

	/**
	 * 商品名称
	 */
	private String goodsName;

	private Date createdTime;

	private Integer createdUser;

	private Date updatedTime;

	private Integer updateUser;

	private Integer goodsId;

	/**
	 * 购物车ID
	 */
	private Integer cartId;

	private String logoUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FSalesOrderModel getOrder() {
		return order;
	}

	public void setOrder(FSalesOrderModel order) {
		this.order = order;
	}

	public FGoodsModel getGoods() {
		return goods;
	}

	public void setGoods(FGoodsModel goods) {
		this.goods = goods;
	}

	public Integer getGoodsVersion() {
		return goodsVersion;
	}

	public void setGoodsVersion(Integer goodsVersion) {
		this.goodsVersion = goodsVersion;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FSalesOrderDetailsModel [id=");
		builder.append(id);
		builder.append(", order=");
		builder.append(order);
		builder.append(", goods=");
		builder.append(goods);
		builder.append(", goodsVersion=");
		builder.append(goodsVersion);
		builder.append(", goodsQty=");
		builder.append(goodsQty);
		builder.append(", price=");
		builder.append(price);
		builder.append(", goodsName=");
		builder.append(goodsName);
		builder.append("]");
		return builder.toString();
	}
}
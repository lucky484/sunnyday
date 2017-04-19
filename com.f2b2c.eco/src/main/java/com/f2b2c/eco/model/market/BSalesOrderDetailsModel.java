package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

public class BSalesOrderDetailsModel implements Serializable {
	/**
	 * 订单主键ID
	 */
	private String id;

	/**
	 * 订单
	 */
	private BSalesOrderModel order;

	/**
	 * 商品ID
	 */
	private BGoodsModel goods;

	/**
	 * 商品版本号
	 */
	private Integer goodsVersion;

	/**
	 * 商品数量
	 */
	private Integer goodsQty;

	/**
	 * 商品价格（分）
	 */
	private Integer price;

	/**
	 * 订单外键
	 */
	private String orderId;

	/**
	 * 商品外键
	 */
	private Integer goodsId;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 是否属于水果类型(0.水果分类 1.非说过分类)
	 */
	private String catalog;

	/**
	 * 退货状态
	 */
	private int goodsStatus;
	
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 小计
     */
    private Integer totalPrice;
    
    /**
     * 佣金百分比
     */
    private String sharePercent;
    
	/**
	 * 是否参与佣金 0:不参与 1：参与
	 */
	private String isCommission;
	
	private ReturnPayModel returnPay;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BSalesOrderModel getOrder() {
		return order;
	}

	public void setOrder(BSalesOrderModel order) {
		this.order = order;
	}

	public BGoodsModel getGoods() {
		return goods;
	}

	public void setGoods(BGoodsModel goods) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public ReturnPayModel getReturnPay() {
		return returnPay;
	}

	public void setReturnPay(ReturnPayModel returnPay) {
		this.returnPay = returnPay;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSharePercent() {
		return sharePercent;
	}

	public void setSharePercent(String sharePercent) {
		this.sharePercent = sharePercent;
	}

	public String getIsCommission() {
		return isCommission;
	}

	public void setIsCommission(String isCommission) {
		this.isCommission = isCommission;
	}
}
package com.f2b2c.eco.model.market;

/**
 * 订单详情表
 * @author jane.hui
 *
 */
public class BtoCBSalesOrderDetailModel {

    /**
     * 订单详情表id
     */
    private String id;

    /**
     * 订单
     */
    private String orderId;

    /**
     * 商品ID
     */
    private Integer goodsId;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}

package com.f2b2c.eco.model.platform;

import java.io.Serializable;

public class FChangeOrderDetailsModel implements Serializable {
    /**
     * 增补单主键ID
     */
    private String id;

    /**
     * 增补单原订单ID
     */
    private FSalesOrderModel changeOrder;

    /**
     * 商品
     */
    private FGoodsModel goods;

    /**
     * 商品版本
     */
    private Integer goodsVersion;

    /**
     * 商品数量
     */
    private Integer goodsQty;

    /**
     *商品单价（分）
     */
    private Integer price;

    /**
     * 增补类型 0增加1减少
     */
    private Integer changetype;

    /**
     * 增减数量
     */
    private Integer changeQty;

    private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FSalesOrderModel getChangeOrder() {
		return changeOrder;
	}

	public void setChangeOrder(FSalesOrderModel changeOrder) {
		this.changeOrder = changeOrder;
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

	public Integer getChangetype() {
		return changetype;
	}

	public void setChangetype(Integer changetype) {
		this.changetype = changetype;
	}

	public Integer getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(Integer changeQty) {
		this.changeQty = changeQty;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FChangeOrderDetailsModel [id=");
		builder.append(id);
		builder.append(", changeOrder=");
		builder.append(changeOrder);
		builder.append(", goods=");
		builder.append(goods);
		builder.append(", goodsVersion=");
		builder.append(goodsVersion);
		builder.append(", goodsQty=");
		builder.append(goodsQty);
		builder.append(", price=");
		builder.append(price);
		builder.append(", changetype=");
		builder.append(changetype);
		builder.append(", changeQty=");
		builder.append(changeQty);
		builder.append("]");
		return builder.toString();
	}

   
}
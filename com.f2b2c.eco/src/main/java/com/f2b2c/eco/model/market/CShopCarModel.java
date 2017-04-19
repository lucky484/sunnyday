package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

public class CShopCarModel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 消费者
     */
    private CUserModel user;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 商铺商品
     */
    private BGoodsModel goods;

    /**
     * 商品数量
     */
    private Integer goodsQty;

    /**
     * 商品版本
     */
    private Integer goodsVersion;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CUserModel getUser() {
		return user;
	}

	public void setUser(CUserModel user) {
		this.user = user;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public BGoodsModel getGoods() {
		return goods;
	}

	public void setGoods(BGoodsModel goods) {
		this.goods = goods;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getGoodsVersion() {
		return goodsVersion;
	}

	public void setGoodsVersion(Integer goodsVersion) {
		this.goodsVersion = goodsVersion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CShopCarModel [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", goods=");
		builder.append(goods);
		builder.append(", goodsQty=");
		builder.append(goodsQty);
		builder.append(", goodsVersion=");
		builder.append(goodsVersion);
		builder.append("]");
		return builder.toString();
	}

    
}
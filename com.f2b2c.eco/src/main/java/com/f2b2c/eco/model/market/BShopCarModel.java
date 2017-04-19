package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

public class BShopCarModel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 消费者用户
     */
    private CUserModel user;
    
    /**
     * 用户外键
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 商品
     */
    private BGoodsModel goods;
    
    /**
     * 商品编号
     */
    private String goodsId;

    /**
     * 商品数量
     */
    private Integer goodsQty;

    /**
     * 商品版本号
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
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
		builder.append("BShopCarModel [id=");
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
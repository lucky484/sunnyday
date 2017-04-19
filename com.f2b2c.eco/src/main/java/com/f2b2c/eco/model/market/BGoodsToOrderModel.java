package com.f2b2c.eco.model.market;

/**
 * 商品信息
 * @author jane.hui
 *
 */
public class BGoodsToOrderModel {

    /**
     * 购物车id
     */
    private Integer cartId;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * logo
     */
    private String logUrl;
    
    /**
     * 单位
     */
    private String goodsUnit;
    
    /**
     * 单价
     */
    private Integer goodsPrice;
    
    /**
     * 商品外键
     */
    private Integer goodsId;
    
    /**
     * 商品数量
     */
    private Integer goodsQuantity;
    
    /**
     * 版本号
     */
    private Integer goodsVersion;

    /**
     * 类别(0.水果  1.非水果)
     */
    private String catalog;
    
    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
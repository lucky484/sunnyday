package com.f2b2c.eco.model.market;

import java.io.Serializable;

/**
 * 购买商品model
 * @author jane.hui
 *
 */
public class BGoodsToPurchaseOrderModel implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = -8998252186565171149L;
    
    /**
     * 店铺Id
     */
    private Integer shopId;
    
    /**
     * 店铺名称
     */
    private String shopName;
    
    /**
     * 商户id
     */
    private Integer bUserId;
    
    /**
     * 商品外键
     */
    private Integer goodsId;
    
    /**
     * 商品编号
     */
    private String goodsNo;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 封面图
     */
    private String logoUrl;
    
    /**
     * 单位
     */
    private String unit;
    
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
     * 锁定数量
     */
    private Integer allocate;
    
    /**
     * 购买数量
     */
    private Integer quantity;
    
    /**
     * 价格
     */
    private Integer price;
    
    /**
     * 版本
     */
    private Integer version;
    
    /**
     * 运费
     */
    private Integer freight;
    
    /**
     * 水果类别(0.水果  1.非水果)
     */
    private String catalog;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public Integer getbUserId() {
        return bUserId;
    }

    public void setbUserId(Integer bUserId) {
        this.bUserId = bUserId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
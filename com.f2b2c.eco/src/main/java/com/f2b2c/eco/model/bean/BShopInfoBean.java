package com.f2b2c.eco.model.bean;

import java.util.List;

/**
 * 店铺信息
 * @author jane.hui
 *
 */
public class BShopInfoBean {
    
    /**
     * 店铺id
     */
    private Integer shopId;
    
    /**
     * 店铺名称
     */
    private String shopName;
    
    /**
     * 商户外键
     */
    private String bUserId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 城市名称
     */
    private String cityName;
    
    /**
     * 省名称
     */
    private String provinceName;
    
    /**
     * 运费
     */
    private Integer freight;
    
    /**
     * 商品信息list
     */
    private List<BGoodsInfoBean> goodsList;

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

    public String getbUserId() {
        return bUserId;
    }

    public void setbUserId(String bUserId) {
        this.bUserId = bUserId;
    }
    
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<BGoodsInfoBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<BGoodsInfoBean> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
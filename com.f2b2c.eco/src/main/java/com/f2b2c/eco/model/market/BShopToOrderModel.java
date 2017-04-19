package com.f2b2c.eco.model.market;

import java.util.List;

/**
 * 店铺信息(前端)
 * @author jane.hui
 *
 */
public class BShopToOrderModel {

    /**
     * 店铺id
     */
    private Integer shopId;
    
    /**
     * 店铺名称
     */
    private String shopName;
    
    /**
     * 卖家外键
     */
    private Integer bUserId;
    
    /**
     * 运费
     */
    private Integer freight;

    /**
     * 商品list
     */
    private List<BGoodsToOrderModel> goodsList;

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

    public List<BGoodsToOrderModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<BGoodsToOrderModel> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getbUserId() {
        return bUserId;
    }

    public void setbUserId(Integer bUserId) {
        this.bUserId = bUserId;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }    
}

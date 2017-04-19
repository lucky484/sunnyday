package com.f2b2c.eco.model.bean;

import java.util.List;

/**
 * 购物车商品bean
 * @author jane.hui
 *
 */
public class BShopCarBean {

    /**
     * 店铺Bean列表
     */
    private List<BShopInfoBean> shopList;

    /**
     * 失效商品信息list
     */
    private List<BGoodsInfoBean> failureGoodsList;

    public List<BShopInfoBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<BShopInfoBean> shopList) {
        this.shopList = shopList;
    }

    public List<BGoodsInfoBean> getFailureGoodsList() {
        return failureGoodsList;
    }

    public void setFailureGoodsList(List<BGoodsInfoBean> failureGoodsList) {
        this.failureGoodsList = failureGoodsList;
    }
}
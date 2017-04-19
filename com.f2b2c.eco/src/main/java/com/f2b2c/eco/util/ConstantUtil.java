package com.f2b2c.eco.util;

/**
 * 常量接口
 * 
 * @author jane.hui
 *
 */
public interface ConstantUtil {

    /**
     * 订单状态
     */
    public interface OrderStatus {
        
        /**
         * 已完成
         */
        public static final Integer COMPLETED = 1;

        /**
         * 待支付
         */
        public static final Integer WAITING_FOR_PAYMENT = 2;
        
        /**
         * 待发货
         */
        public static final Integer TO_BE_SHIPPED = 3;
        
        /**
         * 待收货
         */
        public static final Integer RECEIPT_OF_GOODS = 4;
        
        /**
         * 待评价
         */
        public static final Integer TO_BE_EVALUATED = 5;
    }    
    
    /**
     * 支付方式
     */
    public interface PayType {
        
        /**
         * 支付宝支付
         */
        public static final Integer ALIPAY = 0;

        /**
         * 微信支付
         */
        public static final Integer WEIXIN = 1;
        
        /**
         * 钱包支付
         */
        public static final Integer WALLET_PAY = 2;    
        
    }
    
    public interface RoleType {
        
        /**
         * 超级管理员
         */
        public static final Integer ADMIN_ROLE_ID = 1;
        
        /**
         * 省合伙人
         */
        public static final Integer PROVINCE_ROLE_ID = 2;
        
        /**
         * 市合伙人
         */
        public static final Integer CITY_ROLE_ID = 3;
         
        /**
         * 区合伙人
         */
        public static final Integer AREA_ROLE_ID = 4;

        /**
         * 专属顾问
         */
        public static final Integer COUNSELOR_ROLE_ID = 5;

        /**
         * 财务
         */
        public static final Integer FINANCE_ROLE_ID = 7;

    }
    
    public interface Websocket{
        
        public static final String WEBSOCKET_USERNAME = "userId";
    }

}

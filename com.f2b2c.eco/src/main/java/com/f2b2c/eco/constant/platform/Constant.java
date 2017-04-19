package com.f2b2c.eco.constant.platform;

/**
 * 常量接口类
 * @author brave.chen
 *
 */
public interface Constant {
    /**
     * 角色类型
     * @author brave.chen
     *
     */
    public interface RoleType
    {
        /**
         * 管理员类型
         */
        public static Integer ADMIN_TYPE = 0;
        
        /**
         * 省级代理
         */
        public static Integer PROVINCE_AGENENT_TYPE = 1;
        
        /**
         * 市级代理
         */
        public static Integer CITY_AGENENT_TYPE = 2;
        
        /**
         * 区域代理
         */
        public static Integer AREA_AGENENT_TYPE = 3;
        
        /**
         * 专属顾问
         */
        public static Integer BELONG_AREA_TYPE = 4;
        
        /**
         * 采购类型
         */
        public static Integer PURCHASE_TYPE = 5;
        
        /**
         * 财务类型
         */
        public static Integer FINANCE_TYPE = 6;
        
        /**
         * 物流类型
         */
        public static Integer LOGISTICS_TYPE = 7;
        
        /**
         * 运营类型
         */
        public static Integer OPERATE_TYPE = 8;
    }
    
    public interface OrderType
    {
        /**
         * 所有订单
         */
        public static String ALL_ORDER = "0";
        
        /**
         * 待支付
         */
        public static String WAIT_PAY = "1";
        
        /**
         * 待发货
         */
        public static String WILL_SEND = "2";
        
        /**
         * 已发货
         */
        public static String HAD_SEND = "3";
        
        /**
         * 担保付款
         */
        public static String GUARANTEE_PAY = "4";
        
        /**
         * 已经完成的订单
         */
        public static String HAD_FINISHED = "5";
        
        /**
         * 取消的订单
         */
        public static String HAD_CANCLE = "6";
    }
    
    /**
     * 门店启用禁用类型
     * @author jane.hui
     *
     */
    public interface ShopType
    {
        /**
         * 启用中
         */
        public static String YES = "1";
        
        /**
         * 禁用中
         */
        public static String NO = "0";
    }
    
    /**
     * 补差价订单审核状态
     * @author jane.hui
     *
     */
    public interface CheckStatus
    {
        /**
         * 待审核
         */
        public static String WAIT_FOR_AUDIT = "1";
        
        /**
         * 审核通过
         */
        public static String AUDIT_PASS = "2";
        
        /**
         * 审核未通过
         */
        public static String AUDIT_NOT_PASSED = "3";
    }
    
    /**
     * 首页类别
     * @author jane.hui
     *
     */
    public interface IndexType
    {
        /**
         * 待审核
         */
        public static String ALL = "0";
        
        /**
         * 国产水果
         */
        public static String DOMESTIC_FRUIT = "1";
        
        /**
         * 进口水果
         */
        public static String IMPORTED_FRUIT = "2";
        
        /**
         * 活动商品
         */
        public static String EVENT_MERCHANDISE = "3";
        
        /**
         * 其他商品
         */
        public static String OTHER_GOODS = "4";
    }
    
    // 好享吃商户版当点击二级分类时，全部按钮用0标识
    public static String ALL_Category = "0";
}
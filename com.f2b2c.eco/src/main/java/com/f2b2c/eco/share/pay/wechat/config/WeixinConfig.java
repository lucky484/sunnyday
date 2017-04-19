package com.f2b2c.eco.share.pay.wechat.config;

/**
 * 微信配置
 * 
 * @author jane.hui
 *
 */
public class WeixinConfig {

	/**
     * 微信应用id
     */
    public static String appId = "wxd88a324e474c1646";

	/**
     * 应用密钥
     */
    public static String appsecret = "12f03f5c453f1b7623c8f49c79a6dc01";

    /**
     * 商户号
     */
    public static String partner = "1311584701";

    /**
     * 商户key
     */
    public static String partnerkey = "acyubinb2mingjunc3chenyed4thjac5";
   
    // 服务器异步通知页面路径
    public static String notify_url = "http://shop.hxcchn.com:17145/f2b2c/api/business/order/wx_notify_url";
	
    // 充值服务器异步通知页面路径
    public static String recharge_notify_url = "http://shop.hxcchn.com:17145/f2b2c/api/rechard/wx_notify_url";
}

package com.f2b2c.eco.share.pay.wechat.config;

/**
 * 微信配置
 * 
 * @author jing.liu
 *
 */
public class BWeixinConfig {

	/**
     * 微信应用id
     */
	public static String appId = "wxebf5780af9f3be1a";

	/**
     * 应用密钥
     */
    public static String appsecret = "9579b8c456ab467d53b51fb0f7c1eaa0";

    /**
     * 商户号
     */
    public static String partner = "1311584701";

    /**
     * 商户key
     */
    public static String partnerkey = "acyubinb2mingjunc3chenyed4thjac5";
   
    // 服务器异步通知页面路径
    public static String notify_url = "http://shop.hxcchn.com:17145/f2b2c/api/forder/wx_b_notify_url";
	
    /**
     * F端充值回调地址
     */
    public static String recharge_notify_url = "http://shop.hxcchn.com:17145/f2b2c/api/bRechard/wx_notify_url";
}

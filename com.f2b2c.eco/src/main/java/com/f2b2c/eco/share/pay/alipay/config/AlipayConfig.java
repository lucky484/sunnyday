package com.f2b2c.eco.share.pay.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
    
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088021699073102";

    // 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "";
    
    // 支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    // 签名方式
    public static String sign_type = "RSA";
    
    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    public static String log_path = "D://alipay";
        
    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 接收通知的接口名
    public static String method = "alipay.trade.app.pay";
    
    // 签约卖家支付宝账号
    public static String seller = "3471733714@qq.com";
    
    // 服务器异步通知页面路径
    public static String notify_url = "http://shop.hxcchn.com:17145/qrkitchen/api/business/order/notify_url";
    
    // 充值服务器异步通知页面路径
    public static String recharge_notify_url = "http://shop.hxcchn.com:17145/qrkitchen/api/rechard/notify_url";
    
    // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
    public static String return_url = "http://shop.hxcchn.com:17145/qrkitchen/api/business/order/return_url";
    
    /** 商户私钥，pkcs8格式 */
    public static final String rsa_private = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSExJM3BvTFmYRNvOP0fWrfaxhhwe+JKoJYV+H+HyH2GltFMTgmvgblKfoSx1lyTf3SR7vnzD7uoUewq3FwNMnjoJY4M2zRCCGZqM5r1I8y8pH7BFSFjF9GL+4tPvk8w/8b2e6nxzNksMtWqZ9lZTMk/OXspHEVCCTUbFn6LHh8OkFy0lUy8YOjRLXTk7MFFuixsg6zU1xERqHf93xyAxyor+J69IkqZ/coEGZqWT71mU+DAnxFuYI9FC7hmHxiiObztRQkpWBk8OwO1i9fI9fF0wQ3jFQWWVCDS7nYpoJeXNM2AtdfauH//qUZyb6nBC4VPIf2tV/supgCSFMPjItAgMBAAECggEADx5YM4pIevWDhN6aINWLRr+QGtv96ikjTCs+1vTZ9rloV/jhU5nWegxNwJYdqc5Es5xmzkg8qqEobAGcOK53bE2sogRKZfZ0I1T2Kd9CdLZm7kC5njRb1OW92iEpPQ4HJoplQwR617AL8WLDfpX/u5jIinfZ7GcEpBq9cNK9rnO27rngzRKK+XJS2tKwIrObDazo0LqyHctfO29fVFxoa5CAc6+Qc9uaido2AVjLgdGnuRLFhyllebsXBL3JKwc//PGsWsntrzDuHn0clGrZlmINVNXAD7IR7L7g80HGviHvc3nmFLRlys2fwi+vA8SFpUbGAQuo7ggzZkCrTPAihQKBgQDrGtR2UphdSpwx2UXc62nbYTKTiWW47MiKU0GRjGRTGHm14y9TN9YHXnIF3Sn6O8GXqx1hp7NyvMQwVN8mLl1AUvRKGwGpUf8978AUkXtKWlH3BEacoFsKVoImX7TfsdCUNFz39diWjwgi/Mg1QzTZoZ0T0VePUfjnBzKbJNbRywKBgQCfDplk9N14ZAdcg7FNLaFqIQwXfqkuOBCq0a1+VoeC2sk2LdmW1lSeXQPuO238aLQqIEr858rDJ1S+xTPv1W0pLKLI17FJcLyEQ5/MIl1bxJYXO6cPBz8r5rg4pSo8oRQvnsoMLGkXkbEhnD8SzHFQdWVHkwawz0rsfxFEPAgs5wKBgQDojPRlSZOui9NuN2U0faOKvVzitMBqp8RBltK/nmfKDeat9FdmcE+DoHcWt8oC28LFPBvm8ZO9xVwmFn1X7fqNWsCK4KXHDvVzFUgg0DpstGRo+tDBspf65ND+FiL6NY+EWTswhfxECJzaxsFedei78HFIHgUHHeUe2G7FKS5X+wKBgFQ+MEKHarG0ES4P1h1xeJe0byoWfNas/S7sJT7P57AH3yBiAmfglyrVSxqnwWY5sorJVc5DmmHod/seiZaSbOGs8X78trqWClLnWkCNRvMu2OvvPVk5Vl0TMylVWVyJWB6stIjkELlaAf4yOjnjrHSX6Q+SsK1+7Uj7OBLy1MWrAoGBALjde9+lTehTcp2BS7ewmeyXKZn0vbe71D+yovLtNvmw9D+tGL5HEhU9JLHCJPKYSQWQhO/m5yYKfGgDOjWjIDmuX8xJr+vii2AEnyUgEJqTpgD0qZM/gys/PU/hDo4W8D3s/E1eFtRmCHXVvkI2Sbk58dDXWsw02fv0+2XyQ1++";

    /** 支付宝支付业务：入参app_id */
    public static final String appid = "2017011605130036";
    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}
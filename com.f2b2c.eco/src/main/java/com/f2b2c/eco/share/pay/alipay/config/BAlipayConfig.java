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

public class BAlipayConfig {
	
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088021699073102";

    // 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "";
	
    // 支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
    // 签名方式
	public static String sign_type = "RSA";
	
    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="D://";
		
    // 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

    // 接收通知的接口名
	public static String method = "alipay.trade.app.pay";
	
    // 签约卖家支付宝账号
	public static String seller = "2840524660@QQ.com";
	
    // 服务器异步通知页面路径
    public static String notify_url = "http://115.29.187.163:6656/f2b2c/api/forder/notify_ftb_url";
	
    // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
    public static String return_url = "http://115.29.187.163:6656/f2b2c/api/business/order/return_url";
	
    /** 商户私钥，pkcs8格式 */
	public static final String rsa_private = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJtl6OAan4/82IHcAN7oS0cjNmu8MgWyuZ4i92tIb962DzChYCvusyPz/o5iI6MWif6zywXCyKE/9z3Q5Otbn0mUWbxDHQAUzrPEFXppYu58u+RAqeFIUKUVvZOi78ccyAroAnT9gzGb/R4Z0+2sY398W0U33ywFU6MquutpLWYhAgMBAAECgYBT2xFoZVV3WKTopnSd1NJSZ/EFAAAqTQCXEh1bejJMBwh5YIvKCTwICbFy2giLCC2dER5cVHj5ctATPndZfg6cybmAEpiJ36JAfAAAVjs6U0F5myQUIIB+lFrP3Avg4E7pu4pTt7lUHz2541zDPE7VCqu/2h7iIEAGnmv5S13/5QJBAMj1y8/IOmn8tP4UmzKJQPeSGCzTF1x1yOb9MFxMxx0bIraU/MeIlsFxuFatMLnld4b/HyIDoMNSX4f9UKiC9WMCQQDF9Y5sC2OaIHDxIO4cKfz78jRIYWcK2qvZq85s9Bx7ZwX8E8dujpiP5i9/6aVC6jKoDRcQ2arW+JQe4bay+Z+rAkEAl/8ZPbpjPEDwIHEzhYC8y8QLVvP+ameIIfYEuvszavdYRSLhIoZUCFJcK+rovOIrisayJIb79F/8c54MgLg0owJBAMK670LxYSU4TGGmlxQ1aZsdgrjAt/BBc1NIYmNjLrFlgsmZW1EyDHlhYdBeMidE4lfVqJ72v8t/85T9TA965skCQEqZknZyu8zk6OUnRmfPFeoyFWEvZJ50YSqaBrIqAEFlpN+G5qazpllmTd09E+rQjEaE89PeN6Ru7whCd2YULZs=";

    /** 支付宝支付业务：入参app_id */
    public static final String appid = "2016091901928780";
	
    /**
     * F端充值回调地址
     */
    public static String recharge_notify_url = "http://115.29.187.163:6656/f2b2c/api/bRechard/notify_url";
    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}


package com.f2b2c.eco.share.pay.wechat.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.f2b2c.eco.share.pay.wechat.config.BWeixinConfig;
import net.sf.json.JSONObject;
/**
 * 微信api(请求生成支付订单)
 * @author jing.liu
 *
 */
public class BWeixinUtil {
    
    /**
     * 日志记录器
     */
    private static Logger logger=LoggerFactory.getLogger(BWeixinUtil.class);
    
    @SuppressWarnings("static-access")
	public static String pay(HttpServletRequest request,HttpServletResponse response,Integer money,String product,String orderNo,String notifyUrl){
        // 商户相关信息
        String appid = BWeixinConfig.appId;
        String appsecret = BWeixinConfig.appsecret;
        String partner = BWeixinConfig.partner;
        String partnerkey = BWeixinConfig.partnerkey;
        
        if (StringUtils.isBlank(product))
        {
            product = "支付异常， 切莫付款";
        }
        //获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //商户号
        String mch_id = partner;
        //随机数 
        String nonce_str = strReq;
        
        //商品描述根据情况修改
        String body = product;
        //商户订单号
        String out_trade_no = orderNo;
        
        String total_fee = String.valueOf(money);
        
        String spbill_create_ip = request.getRemoteAddr();
        
/*        String notify_url = UrlHelp.getUrlPathWithContextNoPort(request) + "/api/business/order/wx_notify_url";
        */
        String notify_url = notifyUrl;
        String trade_type = "APP";
        
        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("body", body);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notify_url);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("total_fee", total_fee);
        packageParams.put("trade_type", trade_type);
        
        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>";
        xml += "<appid>"+appid+"</appid>";
    /*    xml += "<attach>"+attach+"</attach>";*/
        xml += "<body>"+body+"</body>";
        xml += "<mch_id>"+mch_id+"</mch_id>";
        xml += "<nonce_str>"+nonce_str+"</nonce_str>";
        xml += "<notify_url>"+notify_url+"</notify_url>";
        xml += "<out_trade_no>"+out_trade_no+"</out_trade_no>";
        xml += "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>";
        xml += "<total_fee>"+total_fee+"</total_fee>";
        xml += "<trade_type>"+trade_type+"</trade_type>";
        xml += "<sign>"+sign+"</sign>";
        xml += "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try
        {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            if (prepay_id.equals(""))
            {
                System.out.println("统一支付接口获取预支付订单出错");
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String packages = "Sign=WXPay";
        finalpackage.put("appid", appid2);
        finalpackage.put("noncestr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("partnerid", mch_id);
        finalpackage.put("prepayid", prepay_id);
        finalpackage.put("timestamp", timestamp);
        String finalsign = reqHandler.createSign(finalpackage);
        JSONObject json = new JSONObject();
        json.put("appid", appid2);
        json.put("mch_id", mch_id);
        json.put("timestamp", timestamp);
        json.put("prepayid", prepay_id);
        json.put("nonce_str", nonceStr2);
        json.put("sign", finalsign);
        System.out.println(json.toString());
        return json.toString();
    }
    
    /**
     * 获取签名
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map) {
        // 商户相关信息
        String appid = BWeixinConfig.appId;
        String appsecret = BWeixinConfig.appsecret;
        String partnerkey = BWeixinConfig.partnerkey;
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.init(appid, appsecret, partnerkey);
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        String sign = requestHandler.createSign(signParams);
        return sign;
    }
    
    /**
     * 验证签名
     * @param xmlString
     * @return
     */
    @SuppressWarnings("unchecked")
	public static boolean checkSign(String xmlString) {

        Map<String, String> map = null;

        try {
            map = XMLUtil.doXMLParse(xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String signFromAPIResponse = map.get("sign").toString();

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

        map.put("sign", "");
/*        map.remove("sign");*/

        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

        String signForAPIResponse = getSign(map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {

            //签名验不过，表示这个API返回的数据有可能已经被篡改了

            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);

            return false;
        }
        System.out.println("恭喜，API返回的数据签名验证通过!!!");
        return true;

    }

    /**
     * 返回成功xml
     * @param return_code
     * @return
     */
    public static String returnXML(String return_code) {
        return "<xml><return_code><![CDATA["
                + return_code + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
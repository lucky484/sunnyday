package com.f2b2c.eco.share.pay.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.f2b2c.eco.share.pay.alipay.config.AlipayConfig;
import com.f2b2c.eco.util.DateUtil;

/**
 * 订单信息工具类
 * @author jane.hui
 *
 */
public class PayUtil {
    
    /**
     * 构造支付订单参数列表
     * @param pid
     * @param app_id
     * @param target_id
     * @return
     */
    public static Map<String, String> buildOrderParamMap(String subject,String body,String price,String outTradeNo,String notify_url) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", AlipayConfig.appid);

        keyValues.put("biz_content", "{\"timeout_express\":\"15d\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+price+"\",\"subject\":\""+subject+"\",\"body\":\""+body+"\",\"out_trade_no\":\"" + outTradeNo +  "\"}");
        
        keyValues.put("notify_url", notify_url);
        
        keyValues.put("charset", AlipayConfig.input_charset);

        keyValues.put("method", AlipayConfig.method);

        keyValues.put("sign_type", "RSA");

        keyValues.put("timestamp", DateUtil.getyyyyMMddhhmmss());

        keyValues.put("version", "1.0");
        
        return keyValues;
    }
    
    /**
     * 构造支付订单参数信息
     * 
     * @param map
     * 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }
    
    /**
     * 拼接键值对
     * 
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
    
    /**
     * 对支付参数信息进行签名
     * 
     * @param map
     *            待签名授权信息
     * 
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }
    
    /**
     * 获取订单信息
     * @param subject:商品名称
     * @param body:商品描述
     * @param price:价格
     * @param outTradeNo:商户订单号
     * @return 返回订单信息
     */
    public static String getInfo(String subject,String body,String price,String outTradeNo,String notify_url){
        Map<String,String> params = PayUtil.buildOrderParamMap(subject, body, price, outTradeNo,notify_url);
        String orderParam = PayUtil.buildOrderParam(params);
        String sign = PayUtil.getSign(params, AlipayConfig.rsa_private);
        final String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }
}
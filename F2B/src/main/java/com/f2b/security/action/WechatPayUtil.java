package com.f2b.security.action;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.MD5Util;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;

/**
 * Created by JianPing on 15/11/18.
 */
public class WechatPayUtil {

    public static String sign(String openid, String total_amount, String mch_billno) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("wxappid", ParamesAPI.appId);
        parameters.put("mch_id", ParamesAPI.partner);
        parameters.put("nonce_str", "ibuaiVcKdpRxkhJA");
        parameters.put("mch_billno", mch_billno);
        parameters.put("send_name", "秋润健康厨房");
        parameters.put("total_num","1");
        parameters.put("wishing","好想 好享吃！乐享安全食品不要停");
        parameters.put("client_ip",ParamesAPI.client_ip);
        parameters.put("act_name","阿克苏苹果圣诞促销");
        parameters.put("remark","阿克苏苹果冰糖心 吃出心情吃出惊喜");
        parameters.put("re_openid",openid);
        parameters.put("total_amount",total_amount);
        String characterEncoding = "UTF-8";
        String mySign = createSign(characterEncoding, parameters);
        System.out.println("我的签名是：" + mySign);
        return mySign;
    }

    /**
     * 分佣
     * @param record
     * @param total_amount
     * @param mch_billno
     * @return
     */
    public static String signForProfit(ShareRecord record, String total_amount, String mch_billno) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("wxappid", ParamesAPI.appId);
        parameters.put("mch_id", ParamesAPI.partner);
        parameters.put("nonce_str", "ibuaiVcKdpRxkhJA");
        parameters.put("mch_billno", mch_billno);
        parameters.put("send_name", "秋润健康厨房");
        parameters.put("total_num","1");
        String nickname;
        if (Base64.getFromBase64(record.getNickname()).length() >= 50) {
			nickname = Base64.getFromBase64(record.getNickname()).substring(0, 50) + "...";
		} else {
			nickname = Base64.getFromBase64(record.getNickname());
		}
        parameters.put("wishing","" + nickname + "通过您分享的链接购买了" + record.getNumber() + "箱阿克苏苹果,这是分润提成,感谢您的支持");
        parameters.put("client_ip",ParamesAPI.client_ip);
        parameters.put("act_name","阿克苏苹果抽奖");
        parameters.put("remark","阿克苏苹果冰糖心 吃出心情吃出惊喜");
        parameters.put("re_openid",record.getShareOpenId());
        parameters.put("total_amount",total_amount);

        String characterEncoding = "UTF-8";
        String mySign = createSign(characterEncoding, parameters);
        System.out.println("我的签名是：" + mySign);
        return mySign;
    }
    
    /**
     * 查询订单
     * @param record
     * @param total_amount
     * @param mch_billno
     * @return
     */
    public static String signForQuery(String orderNo) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", ParamesAPI.appId);
        parameters.put("mch_id", ParamesAPI.partner);
        parameters.put("nonce_str", "D380BFC2BAD727A6B6845193519E3AD6");
        parameters.put("out_trade_no", orderNo);

        String characterEncoding = "UTF-8";
        String mySign = createSign(characterEncoding, parameters);
        System.out.println("我的签名是：" + mySign);
        return mySign;
    }
    
    
    /**
     * 微信支付签名算法sign
     */
    private static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照ascii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + ParamesAPI.payAPIKey);

        System.out.println("签名之前的字符串:"+sb.toString());

        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public static void main(String[] args) {
        System.out.println(sign("111","10","1111111111"));
    }

}

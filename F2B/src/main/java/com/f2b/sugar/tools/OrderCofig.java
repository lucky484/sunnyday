/*
 * @Title: OrderCofig.java
 * @Package com.f2b.sugar.tools
 * @Description: 获取订单号
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年8月20日 下午3:07:32
 * @version V1.0
 */
package com.f2b.sugar.tools;

import com.f2b.sugar.tools.pay.Sha1Util;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;

/**
 * @ClassName: OrderCofig
 * @Description: 获取订单号
 * @author ligang.yang@softtek.com
 * @date 2016年8月20日 下午3:07:32
 *
 */
public class OrderCofig
{
    public static String obtionOrderNo()
    {
        String timestramp = System.currentTimeMillis() + "";
        return ParamesAPI.ORDERNO_PRE + timestramp.substring(0, 7) + Sha1Util.getTimeStamp();   
    }
}

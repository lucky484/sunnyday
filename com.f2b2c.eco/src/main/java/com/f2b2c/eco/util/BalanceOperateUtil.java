package com.f2b2c.eco.util;

import java.math.BigDecimal;

/**
 * 金额操作工具类
 * @author jane.hui
 *
 */
public class BalanceOperateUtil {

    /**
     * 减法
     * @param n1:操作数1
     * @param n2:操作数2
     * @return 返回操作数1-操作数2的结果
     */
    public static Integer minus(Integer n1,Integer n2){
        // 操作数1
        BigDecimal num1 = new BigDecimal(n1);
        // 操作数2 
        BigDecimal num2 = new BigDecimal(n2);
        
        return num1.subtract(num2).intValue();
    }
    
    /**
     * 加法
     * @param n1:操作数1
     * @param n2:操作数2
     * @return 返回操作数1 + 操作数2的结果
     */
    public static Integer add(Integer n1,Integer n2){
        // 操作数1
        BigDecimal num1 = new BigDecimal(n1);
        // 操作数2 
        BigDecimal num2 = new BigDecimal(n2);
        
        return num1.add(num2).intValue();
    }
}

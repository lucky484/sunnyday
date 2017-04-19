package com.f2b2c.eco.util;

import java.math.BigDecimal;

/**
 * 税率工具类
 * @author jane.hui
 *
 */
public class TaxRateUtil {

    /**
     * 计算佣金 的税额(按照工资标准计算)
     * 金额是按分进行计算
     * @return 返回扣税后金额
     */
    public static Integer getCommissionTax(Integer commission){
        // 如果推荐人获得的佣金不满3500元，则不扣税
        if(commission <= 350000){
            return commission;
        }
        Integer money = commission - 350000;
        // 税率
        Integer taxRate = 0;
        if(money < 150000) {
            taxRate = 3;
        } else if(money < 450000) {
            taxRate = 10;
        } else if(money < 900000) {
            taxRate = 20;
        } else if(money < 3500000) {
            taxRate = 25;
        } else if(money < 5500000) {
            taxRate = 30;
        } else if(money < 8000000) {
            taxRate = 35;
        } else {
            taxRate = 45;
        }
        return compute(commission,money,taxRate);
    }
    
    /**
     * 计算佣金的税率
     * @return 返回佣金的税率
     */
    public static BigDecimal getTaxRate(Integer commission){
    	// 税率
        Integer taxRate = 0;
        // 如果推荐人获得的佣金不满3500元，则不扣税
        if(commission <= 350000){
            return new BigDecimal(0);
        }
        Integer money = commission - 350000;
        if(money < 150000) {
            taxRate = 3;
        } else if(money < 450000) {
            taxRate = 10;
        } else if(money < 900000) {
            taxRate = 20;
        } else if(money < 3500000) {
            taxRate = 25;
        } else if(money < 5500000) {
            taxRate = 30;
        } else if(money < 8000000) {
            taxRate = 40;
        } else {
            taxRate = 45;
        }
        return new BigDecimal(taxRate/100);
    }
    
    /**
     * 计算税率
     * @param commision:佣金总额
     * @param money:减去工资基准后的金额
     * @param rate：税率
     * @return 返回扣除个税后的金额
     */
    private static Integer compute(Integer commision,Integer money,Integer taxRate){
        return commision - (money * taxRate / 100);
    }
}
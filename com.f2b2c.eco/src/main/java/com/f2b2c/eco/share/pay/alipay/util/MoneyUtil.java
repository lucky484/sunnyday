package com.f2b2c.eco.share.pay.alipay.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 钱转换
 * @author jane.hui
 *
 */
public class MoneyUtil {
	
    /**
     * 两个操作数相减
     * @param a1:操作数1
     * @param a2:操作数2
     * @return 返回减去后的结果
     */
    public static BigDecimal subtract(final String a1,final String a2){
        BigDecimal a1BigDecimal = new BigDecimal(a1);
        BigDecimal a2BigDecimal = new BigDecimal(a2);
        return a1BigDecimal.subtract(a2BigDecimal);
    }
    
    /**
     * 将元转化为分
     * @param yuan 输入分的价格(输入格式:整数)
     * @return 返回分
     */
    public static int fromYuanToIntFen(final String yuan) { 
        try {
            BigDecimal formatYuan = new BigDecimal(yuan);
            BigDecimal format100 = new BigDecimal(100);
            Integer fen = formatYuan.multiply(format100).intValue();
            return fen;
        } catch(Exception e){
            System.out.println("格式化参数不正确");
            return 0;
        }
    } 
    
    /**
     * 将元转化为分
     * @param yuan 输入分的价格(输入格式:整数)
     * @return 返回分
     */
    public static String fromYuanToFen(final String yuan) { 
        try {
            BigDecimal formatYuan = new BigDecimal(yuan);
            BigDecimal format100 = new BigDecimal(100);
            Integer fen = formatYuan.multiply(format100).intValue();
            return String.valueOf(fen);
        } catch(Exception e){
            System.out.println("格式化参数不正确");
            return "";
        }
    } 
    
    /**
     * 将分转化为元
     * @param fen：输入分的价格(保证价格是整数)
     * @return 返回元
     */
    public static String fromFenToYuan(final String fen) { 
        try {
        DecimalFormat format = new DecimalFormat("0.00");
        BigDecimal formatFen = new BigDecimal(fen);
        BigDecimal format100 = new BigDecimal(100);
        BigDecimal fenBigDecimal = formatFen.divide(format100);
        return format.format(fenBigDecimal);  
        } catch(Exception e){
            System.out.println("格式化参数不正确");
            return "";
        }
    }   
    
    /**
     * 将分转化为元
     * @param fen：输入分的价格(保证价格是整数)
     * @return 返回Integer类型元
     */
    public static int fromFenToIntYuan(final String fen) { 
        try {
        BigDecimal formatFen = new BigDecimal(fen);
        BigDecimal format100 = new BigDecimal(100);
        BigDecimal fenBigDecimal = formatFen.divide(format100);
        return fenBigDecimal.intValue();  
        } catch(Exception e){
            System.out.println("格式化参数不正确");
            return 0;
        }
    }  
    
    /**
     * 两个操作数相除
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal divide(final Integer b1,final Integer b2) { 
        try {
        BigDecimal b1BigDecimal = new BigDecimal(b1);
        BigDecimal b2BigDecimal = new BigDecimal(b2);
        BigDecimal allBigDecimal = b1BigDecimal.divide(b2BigDecimal);
        return allBigDecimal;  
        } catch(Exception e){
            System.out.println("格式化参数不正确‘");
            return new BigDecimal(0);
        }
    }  
    
    /**
     * 两个操作数想除
     * @param fen：输入分的价格(保证价格是整数)
     * @return 返回除以的结果
     */
    public static String divideAndMinus(final Integer b1,final Integer b2,final Integer b3) { 
        try {
            DecimalFormat format = new DecimalFormat("0.00");
            BigDecimal b1BigDecimal = divide(b1,b2);
            BigDecimal b3BigDecimal = new BigDecimal(b3);
            BigDecimal allBigDecimal = b1BigDecimal.subtract(b3BigDecimal);
            return format.format(allBigDecimal);
        } catch(Exception e){
            System.out.println("格式化参数不正确");
            return "0";
        }
    } 
}

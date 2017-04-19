package com.f2b2c.eco.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author jane.hui
 *
 */
public class DateUtil {
    
    /**
     * 返回日期转换格式(yyyy-MM-dd hh:mm:ss)2016-07-29 16:55:53
     * @return
     */
    public static String getyyyyMMddhhmmss(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    public static String getyyyyMMdd(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        return sdf.format(time);
    }
    
    /**
     * 字符串转date
     * @param date
     * @return
     */
    public static Date strParseDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 比较两个是否大于1小时
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareTime(Date date1,Date date2){
         boolean flag = false;
         if(date1.getTime() - date2.getTime() > 60*60*1000){
             flag = true;
         }
         return flag;
    }

    /**
     * 获取上个月第一天的方法
     * @return
     */
    public static String getLastMonthStartDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getyyyyMMdd(calendar.getTime());
    }
    
    /**
     * 获取上个月最后一天的方法
     * @return
     */
    public static String getLastMonthEndDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return getyyyyMMdd(calendar.getTime());
    }
}
package com.f2b2c.eco.util;

/**
 * 计算两个地方之间的距离
 * @author jane.hui
 *
 */
public class DistanceUtil {
	/**
	 * 根据客户端传过来的经纬度坐标来计算与商品的距离
	 * 
	 * @param locationX1 //客户端的经度
	 * @param locationY1 //客户端的纬度
	 * @param locationX2 //商铺的经度
	 * @param locationY2 //商铺的纬度
	 * @return
	 */
	public static double distance(double locationX1, double locationY1, double locationX2, double locationY2) {
		double earthR = 6378137; // 地球的半径（单位米）
		locationY1 = locationY1 * Math.PI / 180.0;
		locationY2 = locationY2 * Math.PI / 180.0;
		double a = locationY1 - locationY2;
		double b = (locationX1 - locationX2) * Math.PI / 180.0;
		double sa2 = Math.sin(a / 2.0);
		double sb2 = Math.sin(b / 2.0);
		double length = 2 * earthR * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(locationY1) * Math.cos(locationY2) * sb2 * sb2));
		return length;
	}
	
	/**
	 * 根据客户端传过来的经纬度坐标来计算与商品的距离
	 * 
	 * @param locationX1 //客户端的纬度
	 * @param locationY1 //客户端的经度
	 * @param locationX2 //商铺的纬度
	 * @param locationY2 //商铺的经度
	 * @return
	 */
	public static double distance2(double locationX1, double locationY1, double locationX2, double locationY2) {
        double lat1 = (Math.PI/180)* locationX1;  
        double lat2 = (Math.PI/180)* locationX2;  
        double lon1 = (Math.PI/180)* locationY1;  
        double lon2 = (Math.PI/180)* locationY2;  
        // 地球半径
        double R = 6371;
      //两点间距离 km，如果想要米的话，结果*1000就可以了  
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;  
        return d*1000;
	}
	
	public static void main(String[] args) {
		System.out.println(distance2(120.275412, 31.471676, 116.68535, 40.325844));
	
	}
}

package com.softtek.mdm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import com.softtek.mdm.bean.IosPolicyVersionBean;

public class IosPushUtil {
	     
	     private static Logger logger = Logger.getLogger(IosPushUtil.class);
	
		 public static final int numberOfThreads = 8;  
         
		 /**
		  * 用线程配推送信息
		  * @param deviceToken
		  * @param alert
		  * @param badge
		  * @param sound
		  * @param map
		  */
		 public static void pushPayloadDevicePairsByThread(List<String> deviceToken, String alert,int badge, String sound,Map<String, String> map){
			 try{
				 if(CollectionUtils.isEmpty(deviceToken)){
					 return ;
				 }
				 List<PayloadPerDevice> payloadDevicePairs = new ArrayList<PayloadPerDevice>();
				 PayloadPerDevice perDevice = null;
				 List<Device> device = new ArrayList<Device>();
				 for (String token : deviceToken){
					 device.add(new BasicDevice(token));
				 }
				 for (int i = 0; i <device.size(); i++) {  
			            perDevice = new PayloadPerDevice(customPayload(alert, badge, sound, map),device.get(i));  
			            payloadDevicePairs.add(perDevice);  
			        }  
			        Push.payloads(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false,numberOfThreads, payloadDevicePairs);  
			 }catch (Exception e1) {
				logger.error("ios push fail");
			 } 
		 }
		 /**
		  * 队列多线程推送
		  * @param deviceToken
		  * @param alert
		  * @param badge
		  * @param sound
		  * @param map
		  */
	     public static void pushDataToIos(List<String> deviceToken, String alert,int badge, String sound,Map<String, String> map){
	    	 if(CollectionUtils.isEmpty(deviceToken)){
				 return ;
			 }   
				try {
					PushQueue queue = Push.queue(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false, numberOfThreads);
					//queue.start();
					PayloadPerDevice perDevice = null;
					List<Device> device = new ArrayList<Device>();
					for (String token : deviceToken){
						device.add(new BasicDevice(token));
					}
					for (int i = 0; i <device.size(); i++) {  
						perDevice = new PayloadPerDevice(customPayload(alert, badge, sound, map), device.get(i));  
						queue.add(perDevice); 
					}
				} catch (Exception e1) {
					logger.error("ios push fail");
				} 
	     }
	     
	     /**
	      * 推送ios设备策略限制类
	      * @param alert
	      * @param badge
	      * @param sound
	      * @param list:device token
	      * @param map : 设备策略限制类
	      */
	     public static void pushIosPolicyLimit(String alert, int badge, String sound, List<String> list,Map<String, String> map) {
				try {
					PushQueue queue = Push.queue(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false, numberOfThreads);
					//queue.start();
					PayloadPerDevice perDevice = null;
					for (String token : list) {
						perDevice = new PayloadPerDevice(customPayloadTr(alert, badge, sound, map), new BasicDevice(token));
						queue.add(perDevice);
					}
				} catch (Exception e1) {
					logger.error("ios push fail");
				} 
	     }
	     
	     /**
	      * 
	      * @param deviceToken
	      * @param alert
	      * @param badge
	      * @param sound
	      * @param map
	      */
	     public static void pushIosPolicyVersion(String alert,int badge, String sound,List<IosPolicyVersionBean> list){
				try {
					PushQueue queue = Push.queue(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false, numberOfThreads);
					//queue.start();
					PayloadPerDevice perDevice = null;
					for (IosPolicyVersionBean bean : list) {
						perDevice = new PayloadPerDevice(customPayloadTo(alert, badge, sound, "version",bean.getVersion()), new BasicDevice(bean.getDeviceToken()));
						queue.add(perDevice);
					}
				} catch (Exception e1) {
					logger.error("ios push fail");
				} 
	     }
	     
		 /**
		  * 队列多线程推送，非静默推送
		  * @param deviceToken
		  * @param alert
		  * @param badge
		  * @param sound
		  * @param map
		  */
	     public static void pushDataToIosTo(List<String> deviceToken, String alert,int badge, String sound,Map<String, String> map){
				try {
					PushQueue queue = Push.queue(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false, numberOfThreads);
					//queue.start();
					PayloadPerDevice perDevice = null;
					List<Device> device = new ArrayList<Device>();
					for (String token : deviceToken){
						device.add(new BasicDevice(token));
					}
					for (int i = 0; i <device.size(); i++) {  
						perDevice = new PayloadPerDevice(customPayloadTo(alert, badge, sound, map), device.get(i));  
						queue.add(perDevice); 
					}
				} catch (Exception e1) {
					logger.error("ios push fail");
				} 
	     }
	     
	     
	     public static void pushDataToIosTr(List<String> deviceToken, String alert,int badge, String sound,Map<String, String> map){
				try {
					PushQueue queue = Push.queue(CommUtil.PUSH_CERT_PATH, CommUtil.PUSH_CERT_PATH_PASSWORD, false, numberOfThreads);
					//queue.start();
					PayloadPerDevice perDevice = null;
					List<Device> device = new ArrayList<Device>();
					for (String token : deviceToken){
						device.add(new BasicDevice(token));
					}
					for (int i = 0; i <device.size(); i++) {  
						perDevice = new PayloadPerDevice(customPayloadTr(alert, badge, sound, map), device.get(i));  
						queue.add(perDevice); 
					}
				} catch (Exception e1) {
					logger.error("ios push fail");
				} 
	     }
	     
	     /**
	      * 构建推送对象，自定义负载,静默推送
	      * @param alert
	      * @param badge
	      * @param sound
	      * @param map
	      * @return
	      */
	     public static PushNotificationPayload customPayload(String alert,int badge, String sound,Map<String, String> map){
	    	 PushNotificationPayload payload = PushNotificationPayload.complex();
	    	 payload.addAlert(alert);   //消息内容
	    	 payload.addBadge(badge);   
	    	 payload.addSound(sound);
	    	 payload.setContentAvailable(true);
	    	 for(Map.Entry<String, String> entry : map.entrySet()){
	    		 payload.addCustomDictionary(entry.getKey(),"1");
	    	 }
	    	 return payload;
	     }
	     
	     /**
	      * 构建推送对象，非静默 推送
	      * @param alert
	      * @param badge
	      * @param sound
	      * @param key
	      * @param value
	      * @return
	      */
	     public static PushNotificationPayload customPayloadTo(String alert,int badge, String sound,String key,String value){
	    	 PushNotificationPayload payload = PushNotificationPayload.complex();
	    	 payload.addAlert(alert);   //消息内容
	    	 payload.addBadge(badge);   
	    	 payload.addSound(sound);
	         payload.addCustomDictionary(key,value);
	    	 return payload;
	     }
	     
	     /**
	      * 构建推送对象，非静默 推送
	      * @param alert
	      * @param badge
	      * @param sound
	      * @param map
	      * @return
	      */
	     public static PushNotificationPayload customPayloadTo(String alert,int badge, String sound,Map<String, String> map){
	    	 PushNotificationPayload payload = PushNotificationPayload.complex();
	    	 payload.addAlert(alert);   //消息内容
	    	 payload.addBadge(badge);   
	    	 payload.addSound(sound);
	    	 if(null != map){
	    		 for(Map.Entry<String, String> entry : map.entrySet()){
	    			 payload.addCustomDictionary(entry.getKey(),entry.getValue());
	    		 }
	    	 }
	    	 return payload;
	     }
	     
	     public static PushNotificationPayload customPayloadTr(String alert,int badge, String sound,Map<String, String> map){
	    	 PushNotificationPayload payload = PushNotificationPayload.complex();
	    	 payload.addAlert(alert);   //消息内容
	    	 payload.addBadge(badge);   
	    	 payload.addSound(sound);
	    	 payload.setContentAvailable(true);
	    	 for(Map.Entry<String, String> entry : map.entrySet()){
	    		 payload.addCustomDictionary(entry.getKey(),entry.getValue());
	    	 }
	    	 return payload;
	     }
}

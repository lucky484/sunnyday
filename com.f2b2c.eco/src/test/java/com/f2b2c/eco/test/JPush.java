package com.f2b2c.eco.test;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPush {
	  
	private static final String appKey = "b3c5cda300c306970e558f21";
	private static final String masterSecret = "ce41f323493520fede45eeaa";
	
	 private final PushClient _pushClient;
	 
	 public JPush(String masterSecret, String appKey) {
		    _pushClient = new PushClient(masterSecret, appKey);
		}
	
	/**
	 * 群推方法(安卓方式的，ios推送和安卓不一样，所以要另外写方法)
	 * 
	 * @param alert
	 * @param title
	 * @param map
	 *            自定义的数据类型，转成json格式的数据放到map中，发往客户端
	 * @return
	 * @throws APIRequestException
	 * @throws APIConnectionException
	 */
	public PushResult buildPayloadList(String alert,String title,Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())
	                .setAudience(Audience.all())
				.setNotification(Notification.ios(alert, map))
	                .build();
		   return _pushClient.sendPush(payload);
	}
	
	/**
	 * 根据别名个推(安卓方式的，ios推送和安卓不一样，所以要另外写方法)
	 * 
	 * @param alias
	 * @param alert
	 * @param title
	 * @param map
	 * @return
	 * @throws APIRequestException
	 * @throws APIConnectionException
	 */
	public PushResult buildPayloadWithNotificationByTag(String tag,String alert,String title,Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.all())
	                .setNotification(Notification.newBuilder().
	                		addPlatformNotification(
	                				AndroidNotification.newBuilder().
	                				setBuilderId(1).
	                				setAlert(alert).
	                				setTitle(title).
	                				addExtras(map).
	                				build())
	                				.build())
	                .build();
		return _pushClient.sendPush(payload);
	}
	
	/**
	 * 根据注册的ID推送
	 * 
	 * @param registrationId
	 * @param alert
	 * @param title
	 * @param map
	 * @return
	 * @throws APIRequestException
	 * @throws APIConnectionException
	 */
	public PushResult buildPayloadWithRegistrationId(String registrationId,String alert,String title,Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.registrationId(registrationId))
	                .setNotification(Notification.android(alert, title, map))
	                .build();
		return _pushClient.sendPush(payload);
	}
	
	/**
	 * 只推送消息，不在通知栏显示
	 * 
	 * @param msgContent
	 * @param map
	 * @return
	 * @throws APIRequestException
	 * @throws APIConnectionException
	 */
	public PushResult buildPayloadMessage(String msgContent, Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.all())
	                .setMessage(Message.newBuilder().
	                		addExtras(map).
	                		setMsgContent(msgContent).
	                		build())
	                		.build();
		  return _pushClient.sendPush(payload);
	}
	
	/**
	 * 根据别名请求
	 * 
	 * @param tag
	 * @param msgContent
	 * @param map
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult buildPayloadWithTags(String tag,String msgContent,Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.tag(tag))
	                .setMessage(Message.newBuilder().
	                		addExtras(map).
	                		setMsgContent(msgContent).
	                		build())
	                		.build();
		  return _pushClient.sendPush(payload);
	}
	
	/**
	 * 根据别名请求
	 * 
	 * @param tag
	 * @param msgContent
	 * @param map
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult buildPayloadWithAlias(String alias,String msgContent,Map<String,String> map) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.alias(alias))
	                .setMessage(Message.newBuilder().
	                		addExtras(map).
	                		setMsgContent(msgContent).
	                		build())
	                		.build();
		  return _pushClient.sendPush(payload);
	}
	
	// 测试方法
    public static void main(String[] args) {
    	JPush jpushClient = new JPush(masterSecret, appKey);
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("111","2222222");
		try {
			jpushClient.buildPayloadList("123", "hello", map);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
}

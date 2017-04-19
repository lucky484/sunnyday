package com.f2b2c.eco.jpush;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPush {

	private static final String appKey = "aaeabf3f8480253d651d91a5";
	private static final String masterSecret = "687f1fb733e0e221eacbb763";

	private final PushClient _pushClient;

	public JPush() {
		_pushClient = new PushClient(masterSecret, appKey);
	}

	/**
     * 群推方法
     * 
     * @param alert
     * @param title
     * @param map
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
	public PushResult buildPayloadPushAll(String alert, String title)throws APIConnectionException, APIRequestException {
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.alert(alert)).build();
		return _pushClient.sendPush(payload);
	}
	
	/**
     * 个推方法
     * 
     * @param registrationId
     * @param alert
     * @param title
     * @param map
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
	public PushResult buildPayloadWithAliaId(String alias,String alert,String title) throws APIConnectionException, APIRequestException{
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(alias))
	                .setNotification(Notification.alert(alert))
	                .build();
		return _pushClient.sendPush(payload);
	}
	
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		JPush jpushClient = new JPush();
    	jpushClient.buildPayloadWithAliaId("35", "hello", "title");
	}
}

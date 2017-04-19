package com.softtek.mdm.thread;

import java.util.List;

import org.apache.commons.collections.KeyValue;

import com.softtek.mdm.queue.MqttQueue;

/**
 * 
 * @author color.wu
 *
 */
public class MqttPushThread implements Runnable {
	/**
	 * 推送的数据内容
	 */
	private List<KeyValue> data;
	
	public MqttPushThread(List<KeyValue> data){
		this.data=data;
	}
	
	@Override
	public void run() {
		MqttQueue.queue(data);
	}

}

package com.softtek.mdm.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.activemq.MqQueuePublisher;
import com.softtek.mdm.util.SpringContextUtils;

public class MqQueueSendThead implements Runnable{
    
	
	private List<String> list = new ArrayList<String>();
	
	private List<String> snList = new ArrayList<String>();
	
	private Map<String,String> map = new HashMap<String, String>();

	/**
	 * 消息发布者对象
	 */
	private static MqQueuePublisher mqQueuePublisher;
	
	public MqQueueSendThead( List<String> list,List<String> snList,Map<String,String> map) {
		if (null == mqQueuePublisher) {
			mqQueuePublisher = (MqQueuePublisher) SpringContextUtils.getBean("mqQueuePublisher");
		}
		this.list = list;
		this.snList = snList;
		this.map = map;
	}

	@Override
	public void run() {
		
		mqQueuePublisher.sendQueueMsg(list,snList, map);
	}

}

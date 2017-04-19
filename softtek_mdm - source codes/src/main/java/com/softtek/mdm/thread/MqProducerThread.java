package com.softtek.mdm.thread;

import java.util.Map;

import javax.jms.Session;

import org.apache.log4j.Logger;

import com.softtek.mdm.activemq.MqPublisher;
import com.softtek.mdm.util.SpringContextUtils;

/**
 * mq消息生产者线程
 * date: May 12, 2016 3:01:37 PM
 *
 * @author brave.chen
 */
public class MqProducerThread extends Thread
{
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(MqProducerThread.class);
	
	/**
	 * 消息主题
	 */
	private String topic;
	
	/**
	 * 消息确认类型
	 */
	private int ackType;
	
	/**
	 * 消息持久化时常
	 */
	private Long timeToLive;
	
	/**
	 * 消息传送模式
	 */
	private Integer deliveryMode;
	
	/**
	 * 消息内容
	 */
	private Map<String, String> map;
	
	/**
	 * 消息发布者对象
	 */
	private static MqPublisher mqPublisher;
	
	/**
	 * 
	 * Creates a new instance of MqProducerThread.
	 *
	 * @param topic 消息主题
	 * @param ackType 消息确认机制为空采空自动确认机制
	 * @param timeToLive 消息持久化时间长度 为空采用默认时间长度
	 * @param deliveryMode 消息是否持久化（跟离线接受相关）
	 * @param map 消息内容map
	 */
	public MqProducerThread(String topic, Integer ackType, Long timeToLive,  int deliveryMode, Map<String, String> map)
	{
		if (null == mqPublisher)
		{
			mqPublisher = (MqPublisher) SpringContextUtils.getBean("mqPublisher");
		}
		this.topic = topic;
		this.ackType = ackType == null ? Session.AUTO_ACKNOWLEDGE : ackType;
		this.timeToLive = timeToLive;
		this.deliveryMode= deliveryMode;
		this.map = map;
	}
	
	@Override
	public void run()
	{
		try
		{
			// 如果消息持久化时间为空表示采用默认持久化时常，时间到了自动清除
			if (null == timeToLive)
			{
				mqPublisher.sendMsgWithDefaultLiveTime(topic, ackType, deliveryMode, map);
			}
			else
			{
				mqPublisher.sendMsg(topic, ackType, timeToLive, deliveryMode, map);
			}
			
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
	}

}


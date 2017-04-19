package com.softtek.mdm.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softtek.mdm.activemq.MqPublisher;
import com.softtek.mdm.util.SpringContextUtils;
/**
 * mqtt 队列
 * @author color.wu
 * @version 1.0
 * 
 * #采用线程处理队列消息推送方法说明#
 * 向N个用户，推送N条消息，存在两种情况：
 * |-1.N个用户，使用相同的消息
 * |-2.N个用户，每个用户使用各自的消息
 * 
 * 使用方法：
 * List<Integer> ids 或者 List<String> ids 
 * |--表示有N个用户，并且topic标识是id
 * Map<String,String> 
 * |--map 表示要发送的消息内容
 * 
 * 第1种情况，
 * List<KeyValue> data=MqttQueue.generateDatas(ids,map);
 * MqttPushThread mqttPushTrd=new MqttPushThread(data);
 * taskExecutor.execute(mqttPushTrd);
 * 
 * -----------------------------------------------------
 * 
 * 第2种情况，
 * List<KeyValue> data=new ArrayList<KeyValue>();
 * foreach(Integer id : ids){
 * 		data.add(MqttQueue.generateKeyValue(String.valueOf(id),map));
 * }
 * MqttPushThread mqttPushTrd=new MqttPushThread(data);
 * taskExecutor.execute(mqttPushTrd);
 */
public class MqttQueue extends ArrayBlockingQueue<KeyValue> {

	private static Logger logger=LoggerFactory.getLogger(MqttQueue.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1683912060661977785L;
	
	/**
	 * 消息发布者对象
	 */
	private static MqPublisher mqPublisher;
	
	public MqttQueue(int capacity) {
		super(capacity);
	}
	
	/**
	 * 将数据放入队列，并进行队列消息发送
	 * @param ids
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public static void queue(List<KeyValue> data){
		int capacity=0;
		if(CollectionUtils.isNotEmpty(data)){
			capacity=data.size();
		}else{
			return ;
		}
		
		MqttQueue queue=new MqttQueue(capacity);
		for (KeyValue kv : data) {
			if(kv!=null){
				queue.add(kv);
			}
		}
		
		if(mqPublisher==null){
			mqPublisher = (MqPublisher) SpringContextUtils.getBean("mqPublisher");
		}
		while (!queue.isEmpty()) {
			KeyValue entry=queue.remove();
			if(entry!=null){
				try {
					if(entry.getValue() instanceof Map<?, ?>){
						mqPublisher.sendMsgWithDefaultLiveTime(String.valueOf(entry.getKey()), 1, 2, (Map<String, String>)entry.getValue());
					}else{
						mqPublisher.sendMsgWithDefaultLiveTime(String.valueOf(entry.getKey()), 1, 2, null);
					}
				} catch (Exception e) {
					logger.error("push msg from queue cause error:"+e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 生成KeyValue，主要是针对每一个用户推送不同的消息
	 * @param str 推送的给指定topic的标识，例如用户ID
	 * @param map 推送的数据
	 * @return
	 */
	public static KeyValue generateKeyValue(String str,Map<String, String> map){
		if(StringUtils.isNotEmpty(str)){
			return new DefaultKeyValue(str, map);
		}
		return null;
	}
	
	/**
	 * 生成推送的数据集合，主要是针对每一个用户推送相同的数据
	 * @param topics 推送的topic的集合,List<String>或者List<Integer>
	 * @param map 推送的数据 Map<String, String>
	 * @return
	 */
	public static List<KeyValue> generateDatas(List<? extends Object> topics,Map<String, String> map){
		if(CollectionUtils.isNotEmpty(topics)){
			List<KeyValue> kvs=new ArrayList<KeyValue>();
			for (Object topic : topics) {
				if(topic instanceof String||topic instanceof Integer){
					kvs.add(generateKeyValue(String.valueOf(topic), map));
				}
			}
			return kvs;
		}
		return null;
	}
	
	/**
	 * 生成推送的单个KeyValue数据
	 * @param topics 单个topics,String或Integer
	 * @param map 推送的数据 Map<String, String>
	 * @return 
	 */
	public static KeyValue generateSingleData(Object topic,Map<String, String> map){
		if(topic!=null&&topic instanceof String&&topic instanceof Integer){
			return generateKeyValue(String.valueOf(topic), map);
		}
		return null;
	}
}

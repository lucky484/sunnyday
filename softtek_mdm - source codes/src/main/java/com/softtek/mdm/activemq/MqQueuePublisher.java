package com.softtek.mdm.activemq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * 设置消息队列发送方式,点对点模式，发送多次
 * @author jing.liu
 *
 */
public class MqQueuePublisher {
	
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(MqPublisher.class);
	
	/**
	 * mq服务器连接地址
	 */
    private String brokerURL;
    
	public void sendQueueMsg(List<String> list,Map<String, String> map){
		Connection connection = null;  
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		
		Session session = null;
		//创建连接
		try {
			connection = (Connection)connectionFactory.createConnection();
			 // 启动连接  
	        connection.start();  
	        // 获取session  
	        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
	        
	        for(int i=0;i<list.size();i++){
	        	// 消息的目的地，消息发送到那个队列  
	        	Destination destination = session.createTopic(list.get(i));  
	        	// 创建消息发送者  
	        	MessageProducer producer = session.createProducer(destination); 
	        	
	        	//创建消息的接收者
	        	MessageConsumer consumer = session.createConsumer(destination);
	        	
	        	//设置消息接收后回调
	        	consumer.setMessageListener(new ClientMessageListener());
	        	
	        	producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
	        	
	        	Message message = getMessage(session,map);
	        	
	        	producer.send(message);  
	        }
		} catch (JMSException e) {
			
			logger.error("connect fail");
			
		} 
	}
	
	/**
	 * 用于给android设备推送消息，包含sn号
	 * @param list
	 * @param list
	 * @param map
	 */
	public void sendQueueMsg(List<String> list,List<String> snList,Map<String, String> map){
		Connection connection = null;  
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		
		Session session = null;
		//创建连接
		try {
			connection = (Connection)connectionFactory.createConnection();
			 // 启动连接  
	        connection.start();  
	        // 获取session  
	        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
            
	        for(int i=0;i<list.size();i++){
	        	map.put("sn", list.get(i));
	        	// 消息的目的地，消息发送到那个队列  
	        	Destination destination = session.createTopic(list.get(i));  
	        	// 创建消息发送者  
	        	MessageProducer producer = session.createProducer(destination); 
	        	
	        	//创建消息的接收者
	        	MessageConsumer consumer = session.createConsumer(destination);
	        	
	        	//设置消息接收后回调
	        	consumer.setMessageListener(new ClientMessageListener());
	        	
	        	producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
	        	
	        	Message message = getMessage(session,map);
	        	
	        	producer.send(message);  
	        }
		} catch (JMSException e) {
			
			logger.error("connect fail");
			
		} 
	}

	  /**
     * 
     * 封装传递消息对象
     *
     * @param session 会话
     * @param map 参数map
     * @return 消息对象
     * @throws JMSException 异常
     */
    private Message getMessage(Session session, Map<String, String> map)
            throws JMSException
    {
        MapMessage message = session.createMapMessage();
        if (map != null && !map.isEmpty())
        {
        	Set<Entry<String, String>> entries=map.entrySet();
        	for (Entry<String, String> entry : entries) {
        		message.setObject(entry.getKey(), entry.getValue());
			}
        }
        return message;
    }
    
    /**
     * 关闭会话
     *
     * @param session 关闭mq会话
     */
    private void closeSession(Session session) {
        try{
            if (session != null) {
                session.close();
            }
        }
        catch (Exception e) {
            logger.error("close jms session failed, error message is " + e.getMessage());
        }
    }

    /**
     * 关闭连接
     *
     * @param connection 连接对象
     */
    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (Exception e) {
        	logger.error("close jms session failed, error message is " + e.getMessage());
        }
    }
    
    private ConnectionFactory connectionFactory;
    public void sendQueueMsg(){
		Connection connection = null;  
		Session session = null;
		//创建连接工厂
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		//创建连接
		try {
			connection = (Connection)connectionFactory.createConnection();
			 // 启动连接  
	        connection.start();  
	        // 获取session  
	        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
            
	        String msg = "1231231";
	        
	        List<String> list = new ArrayList<String>();
	        list.add("123");
	        list.add("456");
	        list.add("789");
	        for(int i=0;i<list.size();i++){
	        	// 消息的目的地，消息发送到那个队列  
	        	Destination destination = session.createTopic(list.get(i));  
	        	// 创建消息发送者  
	        	MessageProducer producer = session.createProducer(destination);    
	        	
	        	producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
	        	
	        	Message message = session.createTextMessage(msg);//getMessage(session,map);
	        	
	        	producer.send(message);  
	        }
		} catch (JMSException e) {
			
			e.printStackTrace();
		} finally{
			
			closeSession(session);
			
			closeConnection(connection);
			
		} 
	}
	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}
	
	public static void main(String[] args) {
		MqQueuePublisher m = new MqQueuePublisher();
		m.sendQueueMsg();
	}
}

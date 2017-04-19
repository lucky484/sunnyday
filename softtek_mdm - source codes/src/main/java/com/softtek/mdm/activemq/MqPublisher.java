package com.softtek.mdm.activemq;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;

/**
 * 设置群推模式，值发送一次，并且消息不做持久化处理，客户端用户在线则受到消息，不在线则收不到消息
 * @author jing.liu
 *
 */
public class MqPublisher
{
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(MqPublisher.class);
	
	/**
	 * mq服务器连接地址
	 */
    private String brokerURL;
    
    /**
     * 设置每个连接中使用的最大活动会话数 
     */
    public int preSessionMaxActiveCon;
    
    /**
     * 设置连接池中的最大连接数  
     */
    public int poolMaxSize;
    
    /**
     * 消息保存时间长度
     */
    public long timeToLive;

    /**
     * 连接池工厂
     */
    private PooledConnectionFactory connectionFactory;
     
    public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}

	public int getPreSessionMaxActiveCon() {
		return preSessionMaxActiveCon;
	}

	public void setPreSessionMaxActiveCon(int preSessionMaxActiveCon) {
		this.preSessionMaxActiveCon = preSessionMaxActiveCon;
	}

	public int getPoolMaxSize() {
		return poolMaxSize;
	}

	public void setPoolMaxSize(int poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	/**
     * Spring bean 初始化方法
     *
     */
    public void init()
    {
    	if (logger.isDebugEnabled())
    	{
    		logger.debug("init mq message publisher start.");
    	}
    	
        ActiveMQConnectionFactory actualConnectionFactory = new ActiveMQConnectionFactory(
                brokerURL);
        actualConnectionFactory.setUseAsyncSend(true);
        connectionFactory = new PooledConnectionFactory(
                actualConnectionFactory);
        connectionFactory.setCreateConnectionOnStartup(true);
        connectionFactory.setMaxConnections(poolMaxSize);

        // 设置每个连接中使用的最大活动会话数
        connectionFactory.setMaximumActiveSessionPerConnection(preSessionMaxActiveCon);
        
        if (logger.isDebugEnabled())
    	{
    		logger.debug("init mq message publisher end.");
    	}
    }

    /**
     * 
     * 生产者发送消息到mq中间件服务器(ackType如果没有特殊原因，暂且用消息制动签收类型交给服务器中间件管理即ackType为1)
     *
     * @author brave.chen
     * @param topic 发送消息主题
     * @param ackType 消息确认类型，0事务类型、1消息自动签收、2客戶端调用acknowledge方法手动签收、3不是必须签收，消息可能会重复发送。
     * @param timeToLive 消息保存时间长度
     * @param deliveryMode 消息传送模式  1（NON_PERSISTENT）类型 只发送一次 离线收不到，2（PERSISTENT）离线后上线还能收到
     * @param map 传递参数【map中必须包含type的key标志是那种类型的数据】
     * @throws Exception 异常【调用方法出请捕获相关异常进行处理和记录】
     */
    public void sendMsg(String topic, int ackType, long timeToLive,  int deliveryMode, Map<String, String> map)
            throws Exception
    {
        Connection connection = null;
        Session session = null;
        try
        {
            // 从连接池工厂中获取一个连接
            connection = connectionFactory.createConnection();

            boolean transacted = Boolean.FALSE;
            if (ackType == Session.SESSION_TRANSACTED)
            {
            	transacted = Boolean.TRUE;
            }
            
            // false 参数表示 为非事务型消息，后面的参数表示消息的确认类型
            session = connection.createSession(transacted, ackType);
            
            MessageProducer producer = session.createProducer(null);
            
            // 离线消息重发设置
            producer.setDeliveryMode(deliveryMode);

            // map convert to javax message
            Message message = getMessage(session, map);
            Destination destination = session.createTopic(topic);
            producer.send(destination, message);
        }
        finally
        {
            closeSession(session);
            closeConnection(connection);
        }
    }
    
    /**
     * 
     * 生产者发送消息到mq中间件服务器(ackType如果没有特殊原因，暂且用消息制动签收类型交给服务器中间件管理即ackType为1)
     *
     * @author brave.chen
     * @param topic 发送消息主题
     * @param ackType 消息确认类型，0事务类型、1消息自动签收、2客戶端调用acknowledge方法手动签收、3不是必须签收，消息可能会重复发送。
     * @param timeToLive 消息保存时间长度
     * @param deliveryMode 消息传送模式  1（NON_PERSISTENT）类型 只发送一次 离线收不到，2（PERSISTENT）离线后上线还能收到
     * @param map 传递参数【只包含一个key-value：key为对应的处理key如推送的是安卓策略key为andriodPolicy】
     * @throws Exception 异常【调用方法出请捕获相关异常进行处理和记录】
     */
    public void sendMsgWithDefaultLiveTime(String topic, int ackType, int deliveryMode, Map<String, String> map)
            throws Exception
    {
    	sendMsg(topic, ackType, timeToLive, deliveryMode, map);
    }

    /**
     * 关闭会话
     *
     * @author brave.chen
     * @param session 关闭mq会话
     */
    private void closeSession(Session session)
    {
        try
        {
            if (session != null)
            {
                session.close();
            }
        }
        catch (Exception e)
        {
            logger.error("close jms session failed, error message is " + e.getMessage());
        }
    }

    /**
     * 关闭连接
     *
     * @author brave.chen
     * @param connection 连接对象
     */
    private void closeConnection(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
        	logger.error("close jms session failed, error message is " + e.getMessage());
        }
    }

    /**
     * 
     * 封装传递消息对象
     *
     * @author brave.chen
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

}
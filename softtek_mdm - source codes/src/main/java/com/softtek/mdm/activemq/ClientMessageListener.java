package com.softtek.mdm.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * 设置客户端消息监听器，监听客户端收到的消息情况
 * @author jing.liu
 *
 */
public class ClientMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
		System.out.println("客户端已经接受到消息，可以进行下面的操作");
	}

}

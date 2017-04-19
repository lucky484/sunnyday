package com.f2b2c.eco.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.f2b2c.eco.util.ConstantUtil.Websocket;

public class SystemWebSocketHandler extends TextWebSocketHandler {

	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
	private static Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);

	@Override
	public void afterConnectionEstablished(WebSocketSession session)throws Exception {
		System.out.println("connect to the websocket success......");
		users.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		 TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");  
	      session.sendMessage(returnMessage);  
	}
	
	@Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
	
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }
	
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
	
	/**
	 * 给某个用户发信息
	 * @param userName
	 * @param message
	 */
    public void sendMessageToUser(String userId, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Websocket.WEBSOCKET_USERNAME).toString().equals(userId)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                	logger.info(e.getMessage());
                }
                break;
            }
        }
    }
}

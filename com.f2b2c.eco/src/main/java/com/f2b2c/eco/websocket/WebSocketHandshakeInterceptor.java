package com.f2b2c.eco.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.Websocket;

/**
 * websocket拦截器
 * @author jing.liu
 *
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor{
    
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
		System.out.println("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		System.out.println("Before Handshake");
		if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
            	BUserModel bUserModel = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
                if (bUserModel == null) {
                	return false;
                }
                attributes.put(Websocket.WEBSOCKET_USERNAME,bUserModel.getId());
                
            }
        }
        return true;
	}

}

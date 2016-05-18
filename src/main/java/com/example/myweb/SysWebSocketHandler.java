package com.example.myweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SysWebSocketHandler implements WebSocketHandler{

	private static final Logger log = LoggerFactory.getLogger(SysWebSocketHandler.class);
	private static final List<WebSocketSession> users = new ArrayList<>();
	
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		 log.debug("链接关闭......" + closeStatus.toString());
	     users.remove(webSocketSession);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("connect success.");
		users.add(session);
        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        if(userName!= null){
            //查询未读消息
            int count = 5;
            session.sendMessage(new TextMessage(count + ""));
        }
		
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
		
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		 if(webSocketSession.isOpen()){
	            webSocketSession.close();
	        }
	        log.debug("error，close connection.");
	        users.remove(webSocketSession);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

	
}

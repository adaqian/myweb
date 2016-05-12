package com.example.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.myweb.bean.UserChatCommand;

@Controller
public class ChatController {
	private SimpMessagingTemplate template;
	
	@Autowired
	public ChatController(SimpMessagingTemplate t) {
		template = t;
	}
	
	/**
     * WebSocket聊天的相应接收方法和转发方法
     *
     * @param userChat 关于用户聊天的各个信息
     */
    @MessageMapping("/userChat")
    public void userChat(UserChatCommand userChat){
    	//找到需要发送的地址
        String dest = "/web/chat" + userChat.getCoordinationId();
        //发送用户的聊天记录
        this.template.convertAndSend(dest, userChat);
    }
}

package com.neighbus.chat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatMapper chatMapper;

    // 롬복 대신 생성자 직접 작성 (생성자가 1개면 @Autowired 생략 가능)
    public ChatController(SimpMessageSendingOperations template, ChatMapper chatMapper) {
        this.template = template;
        this.chatMapper = chatMapper;
    }

    @MessageMapping("/chat/message") 
    public void message(ChatMessageDTO message) {
    	System.out.println("1. 메시지 도착함!");
        System.out.println("2. 받은 내용: " + message.getMessage());
        System.out.println("3. 보낸 사람: " + message.getSender());
        System.out.println("4. 메시지 타입: " + message.getMessageType());
        
        if ("ENTER".equals(message.getMessageType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        chatMapper.insertMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
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
        
        if ("ENTER".equals(message.getMessageType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        chatMapper.insertMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
package com.neighbus.chat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.neighbus.alarm.NotificationService;
import com.neighbus.recruitment.RecruitmentService;
import com.neighbus.account.AccountMapper; // Added

import java.util.List;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatMapper chatMapper;
    private final NotificationService notificationService;
    private final RecruitmentService recruitmentService;
    private final AccountMapper accountMapper; // Added

    public ChatController(SimpMessageSendingOperations template, ChatMapper chatMapper,
                          NotificationService notificationService, RecruitmentService recruitmentService,
                          AccountMapper accountMapper) { // Added
        this.template = template;
        this.chatMapper = chatMapper;
        this.notificationService = notificationService;
        this.recruitmentService = recruitmentService;
        this.accountMapper = accountMapper; // Added
    }

    @MessageMapping("/chat/message") 
    public void message(ChatMessageDTO message) {
        
        if ("ENTER".equals(message.getMessageType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        chatMapper.insertMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        
        // 발신자 ID 조회
        int senderId = accountMapper.findIdByUsername(message.getSender());

        // roomId 포맷에 따라 분기
        if (message.getRoomId().startsWith("friend_")) {
            // 1:1 친구 채팅 알림 로직
            String[] parts = message.getRoomId().replace("friend_", "").split("_");
            int user1 = Integer.parseInt(parts[0]);
            int user2 = Integer.parseInt(parts[1]);

            int recipientId = (senderId == user1) ? user2 : user1;
            
            String notificationContent = message.getSender() + "님으로부터 새로운 메시지: " + message.getMessage();
            // 친구 목록 페이지로 이동하는 URL
            String notificationUrl = "/friend/list";

            notificationService.send(recipientId, "FRIEND_CHAT", notificationContent, notificationUrl);

        } else {
            // 기존 모집글 그룹 채팅 알림 로직
            try {
                int recruitmentId = Integer.parseInt(message.getRoomId());
                List<Integer> memberIds = recruitmentService.getMemberIdsByRecruitmentId(recruitmentId);
                
                String notificationContent = "새로운 채팅 메시지가 도착했습니다: " + message.getMessage();
                String notificationUrl = "/recruitment/" + message.getRoomId();

                for (Integer memberId : memberIds) {
                    if (memberId != senderId) { 
                        notificationService.send(memberId, "CHAT", notificationContent, notificationUrl);
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid recruitment room ID format: " + message.getRoomId());
            }
        }
    }
}
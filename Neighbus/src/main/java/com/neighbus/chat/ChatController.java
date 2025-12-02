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
        System.out.println("ChatController.message() received: " + message.getMessageType() + " from " + message.getSender() + " in room " + message.getRoomId());
        
        if ("ENTER".equals(message.getMessageType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        chatMapper.insertMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        // --- 채팅 알림 추가 ---
        // 1. 모집글 ID (채팅방 ID) 가져오기
        int recruitmentId = Integer.parseInt(message.getRoomId());
        System.out.println("  Recruitment ID: " + recruitmentId);
        
        // 2. 메시지 발신자의 실제 ID 가져오기
        int senderId = accountMapper.findIdByUsername(message.getSender()); // Assuming sender is username
        System.out.println("  Sender Username: " + message.getSender() + ", Sender ID: " + senderId);

        // 3. 해당 모집글의 멤버 ID 목록 가져오기
        List<Integer> memberIds = recruitmentService.getMemberIdsByRecruitmentId(recruitmentId);
        System.out.println("  Members in chat room (IDs): " + memberIds);
        
        // 4. 메시지 발신자를 제외한 모든 멤버에게 알림 전송
        String notificationContent = "새로운 채팅 메시지가 도착했습니다: " + message.getMessage();
        String notificationUrl = "/chat/room/enter/" + message.getRoomId(); // 채팅방으로 이동하는 URL

        for (Integer memberId : memberIds) {
            // 발신자에게는 알림을 보내지 않음
            if (memberId != senderId) { 
                notificationService.send(memberId, "CHAT", notificationContent, notificationUrl);
            }
        }
        // --- End 채팅 알림 ---
    }
}
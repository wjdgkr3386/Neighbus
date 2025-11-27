package com.neighbus.alarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	@Autowired
	private final NotificationMapper notificationMapper; // MyBatis Mapper 주입
	@Autowired
	private final SimpMessagingTemplate messagingTemplate;	

	public NotificationService(NotificationMapper notificationMapper, SimpMessagingTemplate messagingTemplate) {
		super();
		this.notificationMapper = notificationMapper;
		this.messagingTemplate = messagingTemplate;
	}

	@Transactional
	public void send(int receiverId, String type, String content, String url) {
		// 1. DB 저장 (MyBatis)
		NotificationDTO dto = new NotificationDTO();
		dto.setUsersId(receiverId);
		dto.setNotificationType(type);
		dto.setContent(content);
		dto.setUrl(url);

		notificationMapper.save(dto);

		// 2. 실시간 웹소켓 전송
		messagingTemplate.convertAndSendToUser(String.valueOf(receiverId), "/queue/notifications", content // 필요시 DTO
																											// 전체를 보내도 됨
		);
	}
	
	public int countUnread(int userId) {
	    return notificationMapper.countUnread(userId);
	}
	
    public List<NotificationDTO> getMyNotifications(int userId) {
        // Mapper XML에 만들어둔 selectMyNotifications(또는 findUnreadNotifications) 호출
        return notificationMapper.selectMyNotifications(userId);
    }
}

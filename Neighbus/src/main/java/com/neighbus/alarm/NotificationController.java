package com.neighbus.alarm;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbus.account.AccountDTO;

import lombok.RequiredArgsConstructor;

@Controller
public class NotificationController {

    private final NotificationService notificationService; 


   public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}


   // AJAX 요청을 받아서 JSON 리스트 반환
    @GetMapping("/api/notifications")
    @ResponseBody 
    public List<NotificationDTO> getMyNotifications(@AuthenticationPrincipal AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        return notificationService.getMyNotifications(accountDTO.getId());
    }
}

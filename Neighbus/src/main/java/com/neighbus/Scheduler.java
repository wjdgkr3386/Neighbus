package com.neighbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.neighbus.account.AccountService;

@Component
public class Scheduler {

	@Autowired
	AccountService accountService;
	
	// 매 0초 0분 0시에 정지 해제 = 매일 정각에 실행
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void unblockUser() {
		accountService.unblockUser();
	}
}

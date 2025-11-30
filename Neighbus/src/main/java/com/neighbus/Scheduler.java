package com.neighbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.neighbus.account.AccountService;

@Component
public class Scheduler {

	@Autowired
	AccountService accountService;
	
	@Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
	public void unblockUser() {
		accountService.unblockUser();
	}
}

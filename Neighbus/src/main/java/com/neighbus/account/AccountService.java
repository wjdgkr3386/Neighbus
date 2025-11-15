package com.neighbus.account;

import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService{

	int insertSignup(AccountDTO accountDTO) throws Exception;
	
	UserDetails loadUserByUsername(String username) throws Exception;
	
}
package com.neighbus.account;

public interface AccountService{

	int insertSignup(AccountDTO accountDTO) throws Exception;
    
    // ⭐ 새로 추가된 메서드 ⭐
	AccountDTO getAccountInfoByUsername(String username); 
}
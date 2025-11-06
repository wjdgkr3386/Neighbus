package com.neighbus.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountServiceimpl implements AccountService{
	
	@Autowired
	AccountDAO accountDAO;
	
	public int insertSignup(AccountDTO accountDTO) {
		accountDAO.insertSignup(accountDTO);
		return 0;
	}
}

package com.neighbus.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountServiceimpl implements AccountService{
	
	@Autowired
	AccountDAO accountDAO;
	
    // 1. 회원가입 메서드 (기존)
	public int insertSignup(AccountDTO accountDTO) {
		System.out.println("AccountServiceimpl - insertSignup");
		accountDAO.insertSignup(accountDTO);
		return 0;
	}

    // 2. ⭐ 사용자 정보 조회 메서드 추가 ⭐
    // AccountRestController에서 세션 저장을 위해 호출할 메서드입니다.
    @Override
    public AccountDTO getAccountInfoByUsername(String username) {
        System.out.println("AccountServiceimpl - getAccountInfoByUsername");
        // DAO를 호출하여 username으로 전체 AccountDTO 객체를 DB에서 가져옵니다.
        return accountDAO.getAccountInfoByUsername(username); 
    }
}
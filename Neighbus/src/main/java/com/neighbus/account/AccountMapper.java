package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

	//지역 꺼내오기
	List<Map<String, Object>> getProvince();
	List<Map<String, Object>> getCity();
	
	//회원가입
	int checkUsername(AccountDTO accountDTO);
	int insertSignup(AccountDTO accountDTO);
	
	//로그인
	AccountDTO getUser(String username);
}

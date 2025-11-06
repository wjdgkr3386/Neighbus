package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDAO {

	int insertSignup(AccountDTO accountDTO);
	
	//지역 꺼내오기
	List<Map<String, Object>> getProvince();
	List<Map<String, Object>> getRegion();
}

package com.neighbus.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
//필요한 import 추가
import javax.servlet.http.HttpSession;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.Util;

@RestController
public class AccountRestController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountMapper accountMapper;
	
	@PostMapping(value="/insertSignup")
	public Map<String, Object> insertSignup(
			@RequestBody AccountDTO accountDTO
			){
		System.out.println("AccountRestController - insertSignup");
		Map<String, Object> map = new HashMap<String, Object>();
		accountDTO.setUser_uuid(UUID.randomUUID().toString());
		try {
			if(accountMapper.checkUsername(accountDTO)>0) {
				map.put("status", -2);
			}
			accountService.insertSignup(accountDTO);
			map.put("status", 1);
		}catch(Exception e) {
			System.out.println(e);
			map.put("status", -1);
		}
		return map;
	}

	@PostMapping(value="/loginProc")
	public Map<String, Object> loginProc(
	    @RequestBody AccountDTO accountDTO,
	    HttpSession session // ⭐ 1. HttpSession 추가
	){
		System.out.println("AccountRestController - loginProc");
		Map<String, Object> map = new HashMap<String, Object>();
		
		int status = accountMapper.checkLogin(accountDTO);
	    
	    if(status == 1) {
	        // 2. ⭐ AccountService를 이용해 세션에 저장할 사용자 전체 정보 가져오기 ⭐
	        //    (accountService에 getAccountInfoByUsername 같은 메서드가 필요합니다.)
	    	try {
	    		AccountDTO loginUser = accountService.getAccountInfoByUsername(accountDTO.getUsername());
		        // 3. ⭐ 세션에 "loginUser" 이름으로 사용자 객체 저장 ⭐
		        if (loginUser != null) {
		            session.setAttribute("loginUser", loginUser);
		        }
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    }
	    
	    map.put("status", status);
	    return map;
	}
	
}

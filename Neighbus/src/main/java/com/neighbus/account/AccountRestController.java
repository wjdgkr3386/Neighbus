package com.neighbus.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
	AccountDAO accountDAO;
	
	@PostMapping(value="/insertSignup")
	public Map<String, Object> insertSignup(
			@RequestBody AccountDTO accountDTO
			){
		System.out.println("AccountRestController - insertSignup");
		Map<String, Object> map = new HashMap<String, Object>();
		accountDTO.setUser_uuid(UUID.randomUUID().toString());
		try {
			if(accountDAO.checkUsername(accountDTO)>0) {
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
		HttpServletResponse response,
		HttpServletRequest request,
		Model model
	){
		System.out.println("AccountRestController - loginProc");
		Map<String, Object> map = new HashMap<String, Object>();
		
		int status = accountDAO.checkLogin(accountDTO);
		
		if(status == 1) {
			// 쿠키 설정
		    Cookie cookie = new Cookie("username", accountDTO.getUsername());
		    cookie.setMaxAge(60*60*24); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
		    cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
		    response.addCookie(cookie); //response에 Cookie 추가
		    
		    Util.getCookie(request, model);
		}
	    
		map.put("status", status);
		return map;
	}
	
}

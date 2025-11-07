package com.neighbus.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

	@Autowired
	AccountService accountService;
	
	
	@PostMapping(value="/insertSignup")
	public Map<String, Object> insertSignup(
			@RequestBody AccountDTO accountDTO
			){
		System.out.println("AccountRestController - insertSignup");
		System.out.println(accountDTO.getUsername());
		Map<String, Object> map = new HashMap<String, Object>();
		accountDTO.setUser_uuid(UUID.randomUUID().toString());
		try {
			accountService.insertSignup(accountDTO);
			map.put("status", 1);
		}catch(Exception e) {
			System.out.println(e);
			map.put("status", -1);
		}
		return map;
	}
}

package com.neighbus.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.neighbus.account.AccountDTO;
import com.neighbus.account.AccountMapper;

@Controller
public class MainController {
	
	@Autowired
	AccountMapper accountMapper;
	
	@GetMapping(value="/")
	public String mainForm(
		Model model,
		@AuthenticationPrincipal AccountDTO accountDTO
	) {
		System.out.println(accountDTO);
		System.out.println("MainController - mainForm");
		
	    //DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		List<Map<String, Object>> regionList = accountMapper.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		
		return "main/main";
	}
}

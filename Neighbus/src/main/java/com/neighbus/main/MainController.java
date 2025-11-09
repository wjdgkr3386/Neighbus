package com.neighbus.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.neighbus.account.AccountDAO;

@Controller
public class MainController {
	
	@Autowired
	AccountDAO accountDAO;
	
	@GetMapping(value="/")
	public String mainForm(
		Model model
	) {
		System.out.println("MainController - mainForm");
		
	    //DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = accountDAO.getProvince();
		List<Map<String, Object>> regionList = accountDAO.getRegion();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		
		return "main/main";
	}
}

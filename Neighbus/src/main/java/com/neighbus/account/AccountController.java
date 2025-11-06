package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	@Autowired
	AccountDAO accountDAO;
	
	@GetMapping(value={"/",""})
	public String redirectToLogin() {
		System.out.println("AccountController - redirectToLogin");
		return "redirect:/account/login";
	}
	
	@GetMapping(value="/login")
	public String loginForm() {
		System.out.println("AccountController - loginForm");
		return "account/login";
	}
	
	@GetMapping(value="/signup")
	public String signupForm(
		Model model
	) {
		System.out.println("AccountController - signupForm");
		List<Map<String, Object>> provinceList = accountDAO.getProvince();
		List<Map<String, Object>> regionList = accountDAO.getRegion();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		return "account/signup";
	}
}

package com.neighbus.account;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	@Autowired
	AccountMapper accountMapper;
	
	@GetMapping(value={"/",""})
	public String redirectToLogin() {
		System.out.println("AccountController - redirectToLogin");
		return "redirect:/account/login";
	}
	
	@GetMapping(value="/login")
	public String loginForm(
		HttpServletResponse res
	) {
		System.out.println("AccountController - loginForm");
		
		//쿠키 삭제
	    Cookie cookie = new Cookie("username", null);
	    cookie.setMaxAge(0);
	    res.addCookie(cookie);
	    
		return "account/login";
	}
	
	@GetMapping(value="/signup")
	public String signupForm(
		Model model,
		HttpServletResponse res
	) {
		System.out.println("AccountController - signupForm");
		
		//쿠키 삭제
		Cookie cookie = new Cookie("username", null);
	    cookie.setMaxAge(0);
	    res.addCookie(cookie);
	    
	    //DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		List<Map<String, Object>> regionList = accountMapper.getRegion();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		return "account/signup";
	}
}

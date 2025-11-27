package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}


	@GetMapping(value={"/",""})
	public String redirectToLogin() {
		System.out.println("AccountController - redirectToLogin");
		return "redirect:/account/login";
	}
	
	@GetMapping(value="/login")
	public String loginForm(
	) {
		System.out.println("AccountController - loginForm");
		// ★ 로그인 페이지 접속 시에만 SecurityContext를 지웁니다 ★
		// 로그인 성공 후 리다이렉트에서는 SecurityContext를 유지해야 합니다
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null || "anonymousUser".equals(principal)) {
			SecurityContextHolder.clearContext();
		}
		return "account/login";
	}
	
	@GetMapping(value="/signup")
	public String signupForm(
		Model model
	) {
		System.out.println("AccountController - signupForm");
		SecurityContextHolder.clearContext();
	    
	    //DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = accountService.getProvince();
		List<Map<String, Object>> regionList = accountService.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		return "account/signup";
	}

	@GetMapping(value="/find")
	public String signupForm(
	) {
		System.out.println("AccountController - find");
		SecurityContextHolder.clearContext();
		return "account/find";
	}
	
}

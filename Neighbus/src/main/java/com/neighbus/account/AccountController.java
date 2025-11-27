package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/account")
public class AccountController {
	@Autowired
	private final AccountService accountService;
	@Autowired
	private final AccountMapper accountMapper;

	public AccountController(AccountService accountService, AccountMapper accountMapper) {
		super();
		this.accountService = accountService;
		this.accountMapper = accountMapper;
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
	
	@PostMapping("/delMyUser")
	public String delMyUser(HttpServletRequest request, HttpServletResponse response, 
	                        @AuthenticationPrincipal AccountDTO accountDTO) {
	    
	    if (accountDTO != null) {
	        // 1. DB에서 회원 정보 삭제 (이전 질문의 XML 파라미터 타입에 맞춰 DTO 전달)
	        accountMapper.delMyUser(accountDTO); 
	        
	        // 2. 스프링 시큐리티를 이용한 강제 로그아웃 (세션 무효화, 쿠키 삭제 등 포함)
	        new SecurityContextLogoutHandler().logout(request, response, 
	                SecurityContextHolder.getContext().getAuthentication());
	                
	        System.out.println("탈퇴 및 로그아웃 완료");
	    }
	    
	    // 3. 로그인 페이지로 리다이렉트
	    return "redirect:/account/login";
	}
	
}

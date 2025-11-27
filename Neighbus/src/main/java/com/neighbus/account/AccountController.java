package com.neighbus.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping(value="/find")
	public String signupForm(
	) {
		System.out.println("AccountController - find");
		SecurityContextHolder.clearContext();
		return "account/find";
	}
	
	
	@PostMapping("/updateSocialInfo")
	public String updateSocialInfo(@AuthenticationPrincipal AccountDTO accountDTO,
	                               @RequestParam("phone") String phone,
	                               @RequestParam("birth") String birth,
	                               @RequestParam("sex") String sex) {
	    
	    // 1. DTO 정보 수정 (메모리 상의 객체 수정)
	    accountDTO.setPhone(phone);
	    accountDTO.setBirth(birth);
	    accountDTO.setSex(sex);
	    
	    // 2. DB 정보 수정 (영구 저장)
	    accountMapper.updateSocialInfo(accountDTO);
	    
	    // ★ 3. 세션 강제 업데이트 (핵심: 신분증 재발급) ★
	    // 현재 로그인 정보를 가져옴
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    
	    // 수정된 accountDTO를 넣어서 새로운 인증 토큰 생성
	    Authentication newAuth = new UsernamePasswordAuthenticationToken(
	            accountDTO,           // 변경된 유저 정보
	            auth.getCredentials(), // 기존 자격 증명 유지
	            auth.getAuthorities()  // 기존 권한(ROLE) 유지
	    );
	    
	    // 시큐리티 컨텍스트에 새 토큰 등록 (이제 세션도 업데이트됨)
	    SecurityContextHolder.getContext().setAuthentication(newAuth);
	    
	    // 4. 메인으로 이동
	    return "redirect:/";
	}
	
}

package com.neighbus.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neighbus.JwtTokenProvider;

@RestController
@RequestMapping("/api/mobile/account")
public class AccountMobileRestController {


    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AccountMobileRestController(
		AccountService accountService,
        AuthenticationManager authenticationManager,
        AccountMapper accountMapper,
        JwtTokenProvider jwtTokenProvider
    ) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

	@PostMapping(value="/insertSignup")
	public Map<String, Object> insertSignup(
			@RequestBody AccountDTO accountDTO
			){
		System.out.println("AccountRestController - insertSignup");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int status = accountService.insertSignup(accountDTO);
			map.put("status", status);
		}catch(Exception e) {
			System.out.println(e);
			map.put("status", -1);
		}
		return map;
	}
	

    @PostMapping("/findAccount")
    public Map<String, Object> findAccount(
    	@RequestBody Map<String, String> request
    ) {
		System.out.println("AccountRestController - findAccount");
        String email = request.get("email");

        String username = accountService.findUsernameByEmail(email);

        Map<String, Object> result = new HashMap<>();
        if (username == null) {
            result.put("status", 0);
        } else {
            result.put("status", 1);
            result.put("username", username);
        }
        return result;
    }
    
	@PostMapping("/findAccountByEmail")
	public Map<String,Object> findAccountByEmail(
		@ModelAttribute AccountFindDTO accountFindDTO
	) {
		System.out.println("AccountRestController - findAccountByEmail");
		return accountService.findAccountByEmail(accountFindDTO);
	}
	
	@PostMapping("/sendTempPassword")
	public Map<String,Object> sendTempPassword(
		@RequestBody Map<String, String> request
	) {
		System.out.println("AccountRestController - sendTempPassword");
	    Map<String, Object> response = new HashMap<>();
	    try {
	        accountService.sendTempPassword(request.get("email"));
	        response.put("success", true);
	        response.put("message", "임시 비밀번호가 발송되었습니다.");
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "처리 중 오류가 발생했습니다.");
	    }
	    return response;
	}

	@PostMapping("/findAccountByPhone")
	public Map<String,Object> findAccountByPhone(
		@ModelAttribute AccountFindDTO accountFindDTO
	) {
		System.out.println("AccountRestController - findAccountByPhone");
		return accountService.findAccountByPhone(accountFindDTO);
	}
	
	@PostMapping("/updatePasswordByPhone")
	public Map<String,Object> updatePasswordByPhone(
		@RequestBody Map<String, String> request
	) {
	    System.out.println("AccountRestController - updatePasswordByPhone");
	    String phone = request.get("phone");
	    Map<String, Object> response = new HashMap<>();
	    try {
	        accountService.updatePasswordByPhone(phone);
	        response.put("success", true);
	        response.put("message", "임시 비밀번호가 발송되었습니다.");
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "처리 중 오류가 발생했습니다.");
	    }
	    return response;
	}
	
	@PostMapping("/mobileLogin")
	public ResponseEntity<Map<String, String>> mobileLogin(
		@RequestBody Map<String, String> loginData
	) {
	    try {
	        // 1. 앱에서 보낸 데이터 추출
	        String username = loginData.get("username");
	        String password = loginData.get("password");

	        // 2. 인증 시도
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(username, password)
	        );

	        // 3. 인증 성공 시 토큰 생성
	        String token = jwtTokenProvider.createToken(authentication);

	        // 4. 토큰을 담아 응답 (HTTP 200)
	        return ResponseEntity.ok(Map.of("token", token));

	    } catch (AuthenticationException e) {
	        // 인증 실패 시 (HTTP 401)
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body(Map.of("error", "로그인 정보가 올바르지 않습니다."));
	    }
	}
}

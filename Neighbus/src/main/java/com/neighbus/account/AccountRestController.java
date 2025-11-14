package com.neighbus.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountRestController {


    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AccountRestController(AccountService accountService,
                                 AccountMapper accountMapper,
                                 AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.authenticationManager = authenticationManager;
    }

	@PostMapping(value="/insertSignup")
	public Map<String, Object> insertSignup(
			@RequestBody AccountDTO accountDTO
			){
		System.out.println("AccountRestController - insertSignup");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(accountMapper.checkUsername(accountDTO)>0) {
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

}

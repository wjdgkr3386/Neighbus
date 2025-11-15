package com.neighbus.account;

import java.util.UUID;

import org.slf4j.Logger; // ★ Logger import 추가
import org.slf4j.LoggerFactory; // ★ Logger import 추가
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountServiceimpl implements AccountService, UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountServiceimpl.class); // ★ Logger 객체 생성
	
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;
	
	public AccountServiceimpl(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
		passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//비밀번호 암호화해서 회원가입
	public int insertSignup(AccountDTO accountDTO) {
		System.out.println("AccountServiceimpl - insertSignup");
		accountDTO.setUserUuid(UUID.randomUUID().toString());
		accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
		return accountMapper.insertSignup(accountDTO);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDTO accountDTO = accountMapper.getUser(username);
        if (accountDTO == null) {
            throw new UsernameNotFoundException(username);
        }
		
		// ★ 추가된 디버깅 로직: DB에서 로드된 grade 값을 로그로 출력합니다. ★
		log.info("User details loaded. Username: {}, Grade: {}", accountDTO.getUsername(), accountDTO.getGrade());
        
		return accountDTO;
	}
	
}
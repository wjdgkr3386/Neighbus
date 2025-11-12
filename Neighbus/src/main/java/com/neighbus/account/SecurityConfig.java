package com.neighbus.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
        http
        		.csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers( // 로그인 했는지 체크 안해도 되는 경로 지정
                                		"/account"
                                		,"/account/**"
                                		,"/insertSignup"
                                		,"/loginProc"
                                		,"/favicon.ico"
                                		,"/css2/"
                                		,"/.well-known/**"
                                		).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                		.loginPage("/account/login") //로그인 안되어있으면 옮겨질 경로
                        .permitAll()
                );
        return http.build();
    }
}

package com.neighbus.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(
            		csrf -> csrf.ignoringRequestMatchers("/insertSignup","/loginProc","/club/**","/freeboard/**"))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/account/login",
                    "/account/signup",
                    "/insertSignup",
                    "/favicon.ico",
                    "/css/**", "/js/**", "/img/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/account/login")      // 로그인 페이지
                .loginProcessingUrl("/loginProc") // 로그인 처리 URL
                .usernameParameter("username")    // 로그인 폼 username name
                .passwordParameter("password")    // 로그인 폼 password name
                .defaultSuccessUrl("/", true)     // 로그인 성공 후 이동
                .permitAll()
            );
        
        // 어디서 요청됐는지 확인하기
        http.addFilterBefore((request, response, chain) -> {
            HttpServletRequest req = (HttpServletRequest) request; // 캐스팅
            System.out.println("Incoming request URL: " + req.getRequestURI());
            chain.doFilter(request, response);
        }, org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);


        return http.build();
    }
    
}

package com.neighbus;

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
            .csrf().disable() // CSRF 비활성화 (테스트나 간단한 구현 시)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers( 
                        "/account/login",     // 로그인 페이지
                        "/account/signup",    // 회원가입 페이지
                        "/account/insertSignup", // 회원가입 처리
                        "/loginProc",         // 로그인 처리
                        "/css/**",            // CSS, JS, 이미지 등 정적 리소스 (선택사항)
                        "/js/**",
                        "/images/**",
                        "/favicon.ico"
                    ).permitAll() // 위 경로는 로그인 없이 접근 허용
                    .anyRequest().authenticated() // 나머지는 모두 로그인 필요
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/account/login") // 로그인 안되어 있으면 이 페이지로 이동
                .loginProcessingUrl("/loginProc") // 로그인 처리 URL
                .defaultSuccessUrl("/", true) // 로그인 성공 시 이동 경로
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/account/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}

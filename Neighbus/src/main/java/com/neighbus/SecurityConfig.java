package com.neighbus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
            .csrf(										//POST 요청에 사용되는 경로를 적는다. csrf 토큰이 없어도 실행되게 예외처리 한다.
            		csrf -> csrf.ignoringRequestMatchers("/insertSignup","/loginProc", "/logout", "/insertGallery",
            				"/club/**","/freeboard/**","/mypage/**","/recruitment/**"))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                	// 로그인 안해도 접근 가능한 경로
                	"/"
                	,"/account"
                    ,"/account/login"
                    ,"/account/signup"
                    ,"/insertSignup"
                    ,"/gallery"
                    ,"/favicon.ico"
                    ,"/js/**"
                    ,"/img/**"
                    ,"/sys_img/**"
                    ,"/css/**"
            		,"/css2/**"
            		,"/.well-known/**"
                    ,"/auth.js"
                    ,"/error"
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
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")                 // 로그아웃 요청 URL
                    .logoutSuccessUrl("/account/login")   // 로그아웃 후 이동 페이지
                    .invalidateHttpSession(true)          // 세션 무효화
                    .deleteCookies("JSESSIONID")          // 쿠키 삭제
                    .permitAll()
                );
        
//        // 어디서 요청됐는지 확인하기
//        http.addFilterBefore((request, response, chain) -> {
//            HttpServletRequest req = (HttpServletRequest) request; // 캐스팅
//            System.out.println("Incoming request URL: " + req.getRequestURI());
//            chain.doFilter(request, response);
//        }, org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);


        return http.build();
    }
    
}

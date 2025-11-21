package com.neighbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.neighbus.config.CustomAuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
		log.info("========================================");
		log.info("SecurityConfig 생성됨!");
		log.info("CustomAuthenticationSuccessHandler 주입됨: {}", customAuthenticationSuccessHandler != null);
		log.info("========================================");
	}

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

        log.info("========================================");
        log.info("SecurityFilterChain 설정 시작!");
        log.info("사용할 핸들러: {}", customAuthenticationSuccessHandler.getClass().getName());
        log.info("========================================");

        http
            .csrf(										//POST 요청에 사용되는 경로를 적는다. csrf 토큰이 없어도 실행되게 예외처리 한다.
            		csrf -> csrf.ignoringRequestMatchers("/insertSignup","/loginProc", "/logout", "/insertGallery",
            				"/club/**","/freeboard/**","/mypage/**","/api/recruitment/**","/api/inquiry/**","/filterRegion","/ws-stomp/**","/chat/**", "/clubSelect"))
            .authorizeHttpRequests(authorize -> authorize
                
            	// ★ 관리자 전용 경로 추가: /admin/** 경로 접근 시 ROLE_ADMIN 권한을 요구합니다. ★
            	.requestMatchers("/admin", "/admin/**").hasRole("ADMIN")

                .requestMatchers(
                	// 로그인 안해도 접근 가능한 경로
                	"/"
                	,"/about"
                	,"/account"
                    ,"/account/login"
                    ,"/account/signup"
                    ,"/insertSignup"
                    ,"/filterRegion"
                    ,"/favicon.ico"
                    ,"/js/**"
                    ,"/img/**"
                    ,"/sys_img/**"
                    ,"/css/**"
            		,"/css2/**"
            		,"/.well-known/**"
                    ,"/error"
                ).permitAll()
                // 그 외 모든 경로는 인증(로그인)만 되면 접근 가능
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/account/login")      // 로그인 페이지
                .loginProcessingUrl("/loginProc") // 로그인 처리 URL
                .usernameParameter("username")    // 로그인 폼 username name
                .passwordParameter("password")    // 로그인 폼 password name
                .successHandler(customAuthenticationSuccessHandler) // ★ 커스텀 성공 핸들러: grade에 따라 다른 페이지로 이동 ★
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")                 // 로그아웃 요청 URL
                    .logoutSuccessUrl("/account/login")   // 로그아웃 후 이동 페이지
                    .invalidateHttpSession(true)          // 세션 무효화
                    .deleteCookies("JSESSIONID")          // 쿠키 삭제
                    .permitAll()
                );
        
        return http.build();
    }
    
}
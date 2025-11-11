package com.neighbus.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
            .addPathPatterns("/**") //인터셉터 적용 경로
            .excludePathPatterns(
            	    "/account/login"
            	    ,"/loginProc"
            	    ,"/account/signup"
            	    ,"/insertSignup"
            	    // 정적 리소스 추가
            	    ,"/favicon.ico"
            	    ,"/resources/**"
            	    ,"/js/**"
            	    ,"/css/**"
            	    ,"/css2/**"
            	    ,"/img/**"
            	    ,"/sys_img/**"
            	    ,"/*.js"
            	    ,"/*.css"
            	    ,"/*.jpeg"
            	    ,"/*.png"
            	    ,"/auth.js"
            	);
    }
}

package com.neighbus.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor())
            .addPathPatterns("/**") //인터셉터 적용 경로
            .excludePathPatterns(   //인터셉터 하지않을 경로
                "/account/login"
                ,"/loginProc"
                ,"/account/signup"
                ,"/insertSignup"
                ,"/js/**"
                ,"/css/**"
                ,"/css2/**"
                ,"/sys_img/**"
                ,"/img/**"
                ,"/sys_img/**"
                ,"/favicon.ico"
                ,"/resources/**"
        );
    }
}

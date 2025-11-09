package com.neighbus.account;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class CookieInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
    ) throws Exception {

    	//쿠키 체크
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName();
                String value = c.getValue();
                if (name.equals("username") && !value.isEmpty()) {
                    return true;
                }
            }
        }
        
        response.sendRedirect( "/account/login" );
        return false;
    }
}

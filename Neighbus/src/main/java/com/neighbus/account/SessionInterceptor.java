package com.neighbus.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
    ) throws Exception {
    	System.out.println("SessionInterceptor - preHandle");
        
    	HttpSession session = request.getSession(false);
    	System.out.println("session : "+session);
    	if(session == null) {
    		response.sendRedirect( "/account/login" );
            return false;
    	}
        
    	return true;
    }
}

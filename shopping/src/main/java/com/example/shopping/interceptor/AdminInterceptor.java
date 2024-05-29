package com.example.shopping.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor{

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전 로직
        String id = (String) request.getSession().getAttribute("id");
        
        if(id != null && id == "admin") {
        	return true;
        }else{
        	// 관리자가 아닌 경우 접근 차단
            response.sendRedirect("/access-denied");
        	return false;
        }
    }

}

package com.example.shopping.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.shopping.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MemberInterceptor implements HandlerInterceptor{

	@Autowired
	private MemberRepository memberRepo;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전 로직
        String userName = (String) request.getSession().getAttribute("id");
        
//        if(userName != null && !memberRepo.findByUserName(userName).isEmpty()) {
//        	return true;
//        }else{
//        	// 관리자가 아닌 경우 접근 차단
//            response.sendRedirect("/access-denied");
//        	return false;
//        }
        return true;
    }

}

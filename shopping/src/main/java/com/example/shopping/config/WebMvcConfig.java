package com.example.shopping.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.shopping.interceptor.AdminInterceptor;
import com.example.shopping.interceptor.MemberInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private AdminInterceptor adminInterceptor;
	@Autowired
	private MemberInterceptor memberInterceptor;
	
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/images/user/**") // 웹 페이지에서 요청할 URL 경로
            .addResourceLocations("file:///" + uploadPath);// 로드할 이미지 파일의 실제 경로
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**") // 인터셉터를 적용할 URL 패턴
                .excludePathPatterns("/exclude-path"); // 제외할 URL 패턴
        
        registry.addInterceptor(memberInterceptor)
        .addPathPatterns("/member/**") // 인터셉터를 적용할 URL 패턴
        .excludePathPatterns("/member/mem_login") // 제외할 URL 패턴
        .excludePathPatterns("/member/mem_regist"); // 제외할 URL 패턴
    }
    
}

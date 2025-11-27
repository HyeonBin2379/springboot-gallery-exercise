package com.ssg.gallery.common.config;

import com.ssg.gallery.common.interceptor.ApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 WebMvc에 관한 설정 클래스 = web.xml 파일과 동일
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor) // 적용할 인터셉터 등록
                .addPathPatterns("/v1/api/**")  // 인터셉터를 적용할 URL 경로 지정
                .excludePathPatterns("/v1/api/account/**", "/v1/api/items/**"); // 인터셉터를 적용하지 않을 URL 경로 지정
    }
}

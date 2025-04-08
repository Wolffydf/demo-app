package com.example.demoapp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/account/login")
                .excludePathPatterns("/account/register")
                .excludePathPatterns("/playlist/get_playlists") // 测试接口
                .excludePathPatterns("/playlist/randomSongs") // 测试接口
                .excludePathPatterns("/audio/**") // 测试接口
                .excludePathPatterns("/song/**") // 测试接口
                .excludePathPatterns("/collect/**") // 测试接口
                .excludePathPatterns("/history/**"); // 测试接口

    }
}
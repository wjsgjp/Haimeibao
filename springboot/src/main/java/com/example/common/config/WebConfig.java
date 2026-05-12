package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 使用更清晰的配置方式
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")  // 先配置要拦截的路径
                .excludePathPatterns(    // 再配置不需要拦截的路径
                        "/",
                        "/login",
                        "/register",
                        "/files/**",
                        "/error",
                        // 骑手申请相关的所有接口都不需要JWT验证
                        "/riderApply/**"  // 修改这里,使用/**匹配所有子路径
                );
    }
}
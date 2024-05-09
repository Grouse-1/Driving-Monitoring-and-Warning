package com.mxy.mypro.config;

import com.mxy.mypro.interceptor.JWTInterceptor;
import com.mxy.mypro.interceptor.helloInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//标注配置文件
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**") // 对所有路径都进行拦截
                .excludePathPatterns("/login", "/register", "/test"); // 排除路径
    }
}

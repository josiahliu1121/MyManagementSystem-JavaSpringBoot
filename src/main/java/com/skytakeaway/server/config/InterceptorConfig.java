package com.skytakeaway.server.config;

import com.skytakeaway.server.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",        // Exclude login endpoint
                        "/doc.html",          // Exclude Swagger UI documentation page
                        "/swagger-resources/**", // Exclude Swagger resources
                        "/webjars/**",        // Exclude webjar resources for Swagger
                        "/v3/api-docs/**",    // Exclude Swagger API docs
                        "/swagger-ui/**"      // Exclude Swagger UI resources
                );
    }
}

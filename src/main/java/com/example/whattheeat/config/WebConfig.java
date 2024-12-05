package com.example.whattheeat.config;

import com.example.whattheeat.config.interceptor.AuthInterceptor;
import com.example.whattheeat.config.interceptor.OwnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String[] ALL_PATH_PATTERNS = {"/**"};
    private static final String[] WHITE_LIST ={"/users/login", "/users/signup"};
    private static final String[] OWNER_ROLE_REQUIRED_PATH_PATTERNS = {""};


    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns(ALL_PATH_PATTERNS)
                .excludePathPatterns(WHITE_LIST)
                .order(1);

        registry.addInterceptor(new OwnerInterceptor())
                .addPathPatterns(OWNER_ROLE_REQUIRED_PATH_PATTERNS)
                .order(2);
    }
}

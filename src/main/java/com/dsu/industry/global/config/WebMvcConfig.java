package com.dsu.industry.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS - 웹 시큐리티의 중요한 정책 중 하나로 Same-Origin Policy가 있다. 이는 Origin 사이의 리소스 공유에 제한을 거는 것으로 다음 과 같은 위험을 막는 것을 목적으로 하고 있다.
 * 1) XSS(Cross Site Scripting) : 유저가 웹 사이트에 접속하는 것으로 정상적이지 않은 요청이 클라이언트(웹 브라우저)에서 실행되는 것을 나타내며, Cookie 내에 Session 정보를 탈취 하는 것 등의 예시가 있다.
 * 2) CSRF(Cross-Site Request Forgeries) : 로그인 된 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록, 송금 등) 하게 만드는 공격이다. XSS 공격과 유사하며 CSRF 공격은 악성 스크립트를 서버에 요청한다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}

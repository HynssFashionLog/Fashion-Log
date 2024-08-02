package com.example.fashionlog;

import com.example.fashionlog.domain.Role;
import com.example.fashionlog.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스.
 * 애플리케이션의 보안 규칙과 인증/인가 설정을 정의합니다.
 *
 * @author Hynns
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * 보안 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception 보안 구성 중 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                    // 정적 리소르에 대한 접근 허용
                    .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                    // 회원가입 페이지는 모든 사용자에게 접근 허용
                    .requestMatchers("/fashionlog/sign-up").permitAll()
                    // 관리자 페이지는 ADMIN 역할을 가진 사용자만 접근 가능
                    .requestMatchers("/fashionlog/management").hasRole(Role.ADMIN.name())
                    // 공지사항 페이지는 인증된 사용자만 접근 가능
                    .requestMatchers("/fashionlog/notice").authenticated()
                    // 공지사항 새 글 등록은 ADMIN 역할을 가진 사용자만 접근 가능
                    .requestMatchers("/fashionlog/notice/new").hasRole(Role.ADMIN.name())
                    // 공지사항 글은 인증된 사용자만 접근 가능
                    .requestMatchers("/fashionlog/notice/**").authenticated()
                    // /fashionlog/** 경로는 NORMAL 또는 ADMIN 역할을 가진 사용자만 접근 가능
                    .requestMatchers("/fashionlog/**").hasAnyRole(Role.NORMAL.name(), Role.ADMIN.name())
                    // 그 외 모든 요청은 인증된 사용자만 접근 가능
                    .anyRequest().authenticated()
            )
            .formLogin(form ->
                form
                    // 사용자 정의 로그인 페이지 설정
                    .loginPage("/fashionlog/login")
                    // 로그인 처리 URL 설정
                    .loginProcessingUrl("/authenticateTheUser")
                    // 로그인 성공 후 리다이렉트할 기본 URL 설정
                    .defaultSuccessUrl("/fashionlog", true)
                    // 로그인 페이지는 모든 사용자에게 접근 허용
                    .permitAll()
            )
            .logout(LogoutConfigurer::permitAll
            )
            .userDetailsService(customUserDetailsService
            );

        return http.build();
    }

}
